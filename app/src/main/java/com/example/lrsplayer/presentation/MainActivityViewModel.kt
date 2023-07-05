package com.example.lrsplayer.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.domain.model.Playlist
import com.example.lrsplayer.domain.usecase.UseDeleteMusic
import com.example.lrsplayer.domain.usecase.UseGetAllMusicFromLocalDatabase
import com.example.lrsplayer.domain.usecase.UseGetAudioFile
import com.example.lrsplayer.domain.usecase.UseGetMusicImage
import com.example.lrsplayer.domain.usecase.UseGetPlaylistMusics
import com.example.lrsplayer.domain.usecase.UseGetPlaylists
import com.example.lrsplayer.domain.usecase.UseInsertMusicToLocalDatabase
import com.example.lrsplayer.domain.usecase.UseLocalSaveImageByNameAndUri
import com.example.lrsplayer.domain.usecase.UseLocalSavePlaylist
import com.example.lrsplayer.domain.usecase.UseSaveAudioFile
import com.example.lrsplayer.until.Constants
import com.example.lrsplayer.until.Constants.LIGHT_THEME
import com.example.lrsplayer.until.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useGetAllMusicFromLocalDatabase: UseGetAllMusicFromLocalDatabase,
    private val useGetMusicImage: UseGetMusicImage,
    private val useDeleteMusic: UseDeleteMusic,
    private val useSaveAudioFile: UseSaveAudioFile,
    private val useGetAudioFile: UseGetAudioFile,
    private val useInsertMusicToLocalDatabase: UseInsertMusicToLocalDatabase,
    private val useGetPlaylists: UseGetPlaylists,
    private val useLocalSavePlaylist: UseLocalSavePlaylist,
    private val useLocalSaveImageByNameAndUri:UseLocalSaveImageByNameAndUri,
    private val useGetPlaylistMusics: UseGetPlaylistMusics
):ViewModel() {

    private val _currentMainThemeColors = MutableStateFlow(Constants.LIGHT_THEME_COLORS)
    val currentMainThemeColors = _currentMainThemeColors

    private val _musicPause = savedStateHandle.getStateFlow("music_pause",false)
    private val _musics = savedStateHandle.getStateFlow("musics", mutableListOf<Music>())
     val _isLooping = savedStateHandle.getStateFlow("isLooping",false)
    private val _searchText = savedStateHandle.getStateFlow("search_text","")

    private var _musicPayer = MediaPlayer()

    val state = combine(_musicPause,_musics,_isLooping, _searchText){
        data ->
        MusicControlState(
            musicPause = data[0] as Boolean,
            musics = data[1] as List<Music>,
            isLooping = data[2] as Boolean,
            searchText = data[3] as String
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MusicControlState())

    init {
        switchMainThemeColors(LIGHT_THEME)
        getMusics()
    }

    private val _currentMusic = MutableStateFlow<Int?>(null)
    val currentMusic:StateFlow<Int?> = _currentMusic

    private val _showControlScreen = MutableStateFlow(false)
    val showControlScreen:StateFlow<Boolean> = _showControlScreen

    private val _musicTransition = MutableStateFlow(false)
    val musicTransition:StateFlow<Boolean> = _musicTransition

    private val _showSearchBar = MutableStateFlow(false)
    val showSearchBar:StateFlow<Boolean> = _showSearchBar

    private val _showTopBar = MutableStateFlow(true)
    val showTopBar:StateFlow<Boolean> = _showTopBar

    private val _playlistDialogModeState = MutableStateFlow(false)
    val playlistDialogModeState:StateFlow<Boolean> = _playlistDialogModeState

    private var constData:List<Music>? = null

    fun switchPlaylistDialogMode(bool:Boolean){
        _playlistDialogModeState.value = bool
    }

    fun createNewPlaylist(
        name:String,
        uri:Uri?
    ){
        viewModelScope.launch {
            useLocalSavePlaylist.execute(
                Playlist(
                    name = name,
                    imgSrc = if(uri == null) null else name,
                    musics = mutableListOf()
                )
            )
            getPlaylists()
        }
        viewModelScope.launch {
            if(uri != null){
                useLocalSaveImageByNameAndUri.execute(name = name, uri = uri)
            }
        }
    }

    fun getMusics(){
        useGetAllMusicFromLocalDatabase.invoke().onEach { res ->
            when(res){
                is Resource.Success -> {

                    savedStateHandle["musics"] = res.data
                }

                is Resource.Error -> {
                    getMusics()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getPlaylistMusics(id:Int){
        useGetPlaylistMusics.invoke(id).onEach { res ->
            when(res){
                is Resource.Success -> {
                    savedStateHandle["musics"] = res.data
                }

                is Resource.Error -> {
                    getMusics()
                }
            }
        }.launchIn(viewModelScope)
    }

    private val _playlistsList = MutableStateFlow<List<Playlist>>(listOf())
    val playlistsList:StateFlow<List<Playlist>> = _playlistsList

    fun getPlaylists(){
        useGetPlaylists.invoke().onEach {res ->
            when(res){
                is Resource.Success -> {
                    _playlistsList.value = res.data!!
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun switchLooping(){
        savedStateHandle["isLooping"] = !_isLooping.value
        setMusicLooping()
    }

    fun switchSearchBar(){
        _showSearchBar.value = !_showSearchBar.value
    }

    fun setSearchText(text:String){
        viewModelScope.launch {
            savedStateHandle["search_text"] = text
            savedStateHandle["musics"] = constData!!.filter {
                it.name.toLowerCase(Locale.ROOT)
                    .contains(text.toLowerCase(Locale.ROOT)) || it.author!!.toLowerCase(Locale.ROOT)
                    .contains(text.toLowerCase(Locale.ROOT))
            }
        }
    }

    fun switchShowingTopBar(bool:Boolean){
        _showTopBar.value = bool
    }
    fun saveMusic(uri: Uri){

        viewModelScope.launch {
            val fileName = useSaveAudioFile.execute(uri)
            val file = useGetAudioFile.execute(fileName)

            useInsertMusicToLocalDatabase.execute(
                Music(
                    name = fileName,
                    path = file.absolutePath
                )
            )
        }
    }
    fun setCurrentMusic(id:Int?){
        _currentMusic.value = id
        setMusicTransition(false)
    }
    fun setMusicTransition(bool:Boolean){
        _musicTransition.value = bool
    }

    fun shuffleMusic(){
        stopMusic()
        savedStateHandle["musics"] = _musics.value.shuffled()
        if(_currentMusic.value == null) setCurrentMusic(0)
        else nextMusic()
    }
    fun nextMusic(){
        val cns = _musics.value.size - 1
        if(_currentMusic.value!! >= cns) setCurrentMusic(0)
        else setCurrentMusic(_currentMusic.value!! + 1)
    }
    fun stopMusic(){
        _musicPayer.stop()
        _musicPayer.release()
        _musicPayer = MediaPlayer()
    }
    fun continueMusic(){
        savedStateHandle["music_pause"] = false
        _musicPayer.start()
        setMusicTransition(false)
    }
    fun setMusicLooping(){
        Log.d("sdfsdfsdfsd",_isLooping.value.toString())
        _musicPayer.isLooping = _isLooping.value
    }

    fun pauseMusic(){
        savedStateHandle["music_pause"] = true
        _musicPayer.pause()
        setMusicTransition(false)
    }
    fun playMusic(music: Music, context: Context){
        _musicPayer.setDataSource(context, music.path.toUri())
        _musicPayer.prepare()
        _musicPayer.start()
        savedStateHandle["music_pause"] = false
    }
    fun lastMusic(){
        if(_currentMusic.value == 0) setCurrentMusic(_musics.value.size - 1)
        else {
            Log.d("sdfsdfsdfdf",(_currentMusic.value!! - 1).toString())
            setCurrentMusic(_currentMusic.value!! - 1)
        }
    }

    fun deleteMusic(cnx: Context){
        viewModelScope.launch {
            useDeleteMusic.deleteMusic(_musics.value[_currentMusic.value!!])
            val last = _musics.value
            last.remove(last[_currentMusic.value!!])
            savedStateHandle["musics"] = last
            if(_musics.value.size == 1 && _currentMusic.value == 0) {
                stopMusic()
                playMusic(_musics.value[0], cnx)
                pauseMusic()
                continueMusic()
            }
            if(_musics.value.size == 0) {
                setCurrentMusic(null)
                _showControlScreen.value = false
            }
            else if (_musics.value.size >= 1) {
                Log.d("sfsdfsdfsdf","######")
                lastMusic()
            }
        }
    }


    fun switchControlScreen(bool:Boolean){
         _showControlScreen.value = bool
    }
    fun getCurrentMusicPosition(): Int {
        return _musicPayer.currentPosition + 500
    }


    fun setMusicPosition(progress:Float){
        val dur = _musicPayer.duration
        _musicPayer.seekTo((dur*(progress)).toInt())
    }
    fun getMusicDuration():String {
        if(_musicPayer.duration == -1) return ""
        return convertMillisecondsToNormalTime(_musicPayer.duration)
    }
    fun getMusicPercProgress():Float{
        val dur = _musicPayer.duration
        val pos = getCurrentMusicPosition()
        Log.d("sfdfgdfg","$dur    $pos")
        return pos.toFloat() * 100 / dur / 100
    }
    fun getMusicImage(file: String) = useGetMusicImage.execute(file)



    fun switchMainThemeColors(theme:String){
        when(theme){
            LIGHT_THEME -> _currentMainThemeColors.value = Constants.LIGHT_THEME_COLORS
            else ->  _currentMainThemeColors.value = Constants.DARK_THEME_COLORS
        }
    }
    @SuppressLint("DefaultLocale")
    private fun convertMillisecondsToNormalTime(total:Int):String{
        return String.format(
            "%02d:%02d ",
            TimeUnit.MILLISECONDS.toMinutes(total.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(total.toLong()) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(total.toLong()))
        )
    }

}