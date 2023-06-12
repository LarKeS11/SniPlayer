package com.example.lrsplayer.presentation.screen.music_screen

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.domain.usecase.*
import com.example.lrsplayer.until.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val useSaveAudioFile: UseSaveAudioFile,
    private val useGetAudioFile: UseGetAudioFile,
    private val useInsertMusicToLocalDatabase: UseInsertMusicToLocalDatabase,
    private val useGetAllMusicFromLocalDatabase: UseGetAllMusicFromLocalDatabase,
    private val savedStateHandle: SavedStateHandle,
    private val useGetMusicImage: UseGetMusicImage,
    private val useDeleteMusic: UseDeleteMusic
):ViewModel() {

    private val _isLoading = savedStateHandle.getStateFlow("isLoading", false)
    private val _data = savedStateHandle.getStateFlow("data", mutableListOf<Music>())
    private val _error = savedStateHandle.getStateFlow("error","")
    private val _currentMusic = savedStateHandle.getStateFlow<Int?>("current_music", null)
    private val _musicPause = savedStateHandle.getStateFlow("music_pause",false)
    private val _musicLooping = savedStateHandle.getStateFlow("music_loop",false)
    private val _searchText = savedStateHandle.getStateFlow("search_text","")

    private var _musicPayer = MediaPlayer()

    private var constData:List<Music>? = null

    val state = combine(_isLoading, _data, _error, _currentMusic, _musicPause, _musicLooping, _searchText){
        data ->
        MusicState(
            isLoading = data[0] as Boolean,
            data = data[1] as List<Music>,
            error = data[2] as String,
            currentMusic = data[3] as Int?,
            musicPause = data[4] as Boolean,
            isLooping = data[5] as Boolean,
            searchText = data[6] as String
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MusicState())


    private val _showControlScreen = MutableStateFlow<Boolean?>(null)
    val showControlScreen = _showControlScreen



    private val _musicTransition = MutableStateFlow(false)
    val musicTransition = _musicTransition

    private val _showSearchBar = MutableStateFlow(false)
    val showSearchBar:StateFlow<Boolean> = _showSearchBar


    init {
        getMusic()
    }

    fun stopMusic(){
        _musicPayer.stop()
        _musicPayer.release()
        _musicPayer = MediaPlayer()
    }

    fun setCurrentMusic(index:Int?){
        savedStateHandle["current_music"] = index

    }

    fun setSearchText(text:String){
        viewModelScope.launch {
            savedStateHandle["search_text"] = text
            savedStateHandle["data"] = constData!!.filter {
                it.name.toLowerCase(Locale.ROOT)
                    .contains(text.toLowerCase(Locale.ROOT)) || it.author!!.toLowerCase(Locale.ROOT)
                    .contains(text.toLowerCase(Locale.ROOT))
            }
        }
    }

    fun playMusic(music: Music, context: Context){
        _musicPayer.setDataSource(context, music.path.toUri())
        _musicPayer.prepare()
        _musicPayer.start()
        savedStateHandle["music_pause"] = false
        setMusicTransition(false)
    }

    fun shuffleMusic(){
        stopMusic()
        savedStateHandle["data"] = _data.value.shuffled()
        if(_currentMusic.value == null) setCurrentMusic(0)
        else nextMusic()
    }

    fun getCurrentMusicPosition(): Int {
        return _musicPayer.currentPosition + 500
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

    fun setMusicTransition(bool:Boolean){
        _musicTransition.value = bool
    }

    fun setMusicPosition(progress:Float){
        val dur = _musicPayer.duration
        _musicPayer.seekTo((dur*(progress)).toInt())
    }

    fun nextMusic(){
        val cns = _data.value.size - 1
        if(_currentMusic.value!! >= cns) setCurrentMusic(0)
        else setCurrentMusic(_currentMusic.value!! + 1)
    }

    fun lastMusic(){
        if(_currentMusic.value == 0) setCurrentMusic(_data.value.size - 1)
        else setCurrentMusic(_currentMusic.value!! - 1)
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

    fun pauseMusic(){
        savedStateHandle["music_pause"] = true
        _musicPayer.pause()
    }

    fun continueMusic(){
        savedStateHandle["music_pause"] = false
        _musicPayer.start()
    }
    fun getMusicImage(file: String) = useGetMusicImage.execute(file)

    fun saveMusic(uri:Uri){

        viewModelScope.launch {
            val fileName = useSaveAudioFile.execute(uri)
            val file = useGetAudioFile.execute(fileName)

            useInsertMusicToLocalDatabase.execute(
                Music(
                    name = fileName,
                    path = file.absolutePath
                )
            )
            getMusic()
        }
    }
    fun switchLooping(){
        _musicPayer.isLooping = !_musicLooping.value
        savedStateHandle["music_loop"] = !_musicLooping.value
    }
    fun setMusicLooping(){
        _musicPayer.isLooping = _musicLooping.value
    }

    fun switchControlScreenState(){
        if(showControlScreen.value == null){
            _showControlScreen.value = true
        }
        _showControlScreen.value = !showControlScreen.value!!
    }

    fun switchSearchBar(){
        _showSearchBar.value = !_showSearchBar.value
    }

    fun deleteMusic(cnx:Context){
        viewModelScope.launch {
            useDeleteMusic.deleteMusic(_data.value[_currentMusic.value!!])
            val last = _data.value
            last.remove(last[_currentMusic.value!!])
            savedStateHandle["data"] = last
            stopMusic()
            if(_data.value.size == 1 && _currentMusic.value == 0) {
                playMusic(_data.value[0], cnx)
                pauseMusic()
                continueMusic()
            }
            if(_data.value.size == 0) {
                setCurrentMusic(null)
                _showControlScreen.value = null
            }
            else if (_data.value.size >= 1) lastMusic()
        }
    }
    private fun getMusic(text:String? = null){

        useGetAllMusicFromLocalDatabase.invoke(text).onEach { res ->

            when(res){
                is Resource.Loading -> {
                    savedStateHandle["isLoading"] = true
                    savedStateHandle["error"] = ""
                }
                is Resource.Success -> {
                    savedStateHandle["isLoading"] = false
                    savedStateHandle["error"] = ""
                    savedStateHandle["data"] = res.data
                    constData = res.data
                }
                is Resource.Error -> {
                    savedStateHandle["isLoading"] = false
                    savedStateHandle["error"] = res.message
                }
            }

        }.launchIn(viewModelScope)

    }



}

