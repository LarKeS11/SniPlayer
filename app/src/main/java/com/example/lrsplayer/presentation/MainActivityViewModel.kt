package com.example.lrsplayer.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.domain.usecase.UseDeleteMusic
import com.example.lrsplayer.domain.usecase.UseGetAllMusicFromLocalDatabase
import com.example.lrsplayer.domain.usecase.UseGetMusicImage
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
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useGetAllMusicFromLocalDatabase: UseGetAllMusicFromLocalDatabase,
    private val useGetMusicImage: UseGetMusicImage,
    private val useDeleteMusic: UseDeleteMusic
):ViewModel() {

    private val _currentMainThemeColors = MutableStateFlow(Constants.LIGHT_THEME_COLORS)
    val currentMainThemeColors = _currentMainThemeColors

    private val _musicPause = savedStateHandle.getStateFlow("music_pause",false)
    private val _musics = savedStateHandle.getStateFlow("musics", mutableListOf<Music>())
    private val _isLooping = savedStateHandle.getStateFlow("isLooping",false)

    private var _musicPayer = MediaPlayer()

    val state = combine(_musicPause,_musics,_isLooping){
        data ->
        MusicControlState(
            musicPause = data[0] as Boolean,
            musics = data[1] as List<Music>,
            isLooping = data[2] as Boolean
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MusicControlState())

    init {
        switchMainThemeColors(LIGHT_THEME)
        useGetAllMusicFromLocalDatabase.invoke().onEach { res ->
            when(res){
                is Resource.Success -> {
                    savedStateHandle["musics"] = res.data
                }
            }
        }.launchIn(viewModelScope)
    }

    private val _currentMusic = MutableStateFlow<Int?>(null)
    val currentMusic:StateFlow<Int?> = _currentMusic

    private val _showControlScreen = MutableStateFlow(false)
    val showControlScreen:StateFlow<Boolean> = _showControlScreen

    private val _musicTransition = MutableStateFlow(false)
    val musicTransition:StateFlow<Boolean> = _musicTransition

    fun switchLooping(){
        savedStateHandle["isLooping"] = !_isLooping.value
    }
    fun setCurrentMusic(id:Int?){
        _currentMusic.value = id
    }
    fun setMusicTransition(bool:Boolean){
        _musicTransition.value = bool
    }

    fun shuffleMusic(){
        stopMusic()
        savedStateHandle["data"] = _musics.value.shuffled()
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
    }
    fun pauseMusic(){
        savedStateHandle["music_pause"] = true
        _musicPayer.pause()
    }
    fun playMusic(music: Music, context: Context){
        _musicPayer.setDataSource(context, music.path.toUri())
        _musicPayer.prepare()
        _musicPayer.start()
        savedStateHandle["music_pause"] = false
        setMusicTransition(false)
    }
    fun lastMusic(){
        if(_currentMusic.value == 0) setCurrentMusic(_musics.value.size - 1)
        else setCurrentMusic(_currentMusic.value!! - 1)
    }

    fun deleteMusic(cnx: Context){
        viewModelScope.launch {
            useDeleteMusic.deleteMusic(_musics.value[_currentMusic.value!!])
            val last = _musics.value
            last.remove(last[_currentMusic.value!!])
            savedStateHandle["data"] = last
            stopMusic()
            if(_musics.value.size == 1 && _currentMusic.value == 0) {
                playMusic(_musics.value[0], cnx)
                pauseMusic()
                continueMusic()
            }
            if(_musics.value.size == 0) {
                setCurrentMusic(null)
                _showControlScreen.value = false
            }
            else if (_musics.value.size >= 1) lastMusic()
        }
    }


    fun switchControlScreen(){
        _showControlScreen.value = !_showControlScreen.value
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