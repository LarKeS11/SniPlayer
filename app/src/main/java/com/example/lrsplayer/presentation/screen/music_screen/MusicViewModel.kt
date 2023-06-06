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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
):ViewModel() {

    private val _isLoading = savedStateHandle.getStateFlow("isLoading", false)
    private val _data = savedStateHandle.getStateFlow("data", listOf<Music>())
    private val _error = savedStateHandle.getStateFlow("error","")
    private val _currentMusic = savedStateHandle.getStateFlow<Int?>("current_music", null)
    private val _musicPause = savedStateHandle.getStateFlow("music_pause",false)

    private var _musicPayer = MediaPlayer()

    val state = combine(_isLoading, _data, _error, _currentMusic, _musicPause){
        isLoading, data, error, currentMusic, musicPause ->
        MusicState(
            isLoading = isLoading,
            data = data,
            error = error,
            currentMusic = currentMusic,
            musicPause = musicPause
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MusicState())


    private val _showControlScreen = MutableStateFlow(false)
    val showControlScreen = _showControlScreen



    private val _musicTransition = MutableStateFlow(false)
    val musicTransition = _musicTransition


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

    fun playMusic(music: Music, context: Context){
        _musicPayer.setDataSource(context, music.path.toUri())
        _musicPayer.prepare()
        _musicPayer.start()
        savedStateHandle["music_pause"] = false
        setMusicTransition(false)
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

    fun nextMusic(){
        val cns = _data.value.size - 1
        if(_currentMusic.value == cns) setCurrentMusic(0)
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

    fun switchControlScreenState(){
        _showControlScreen.value = !showControlScreen.value
    }
    private fun getMusic(){

        useGetAllMusicFromLocalDatabase.invoke().onEach { res ->

            when(res){
                is Resource.Loading -> {
                    savedStateHandle["isLoading"] = true
                    savedStateHandle["error"] = ""
                }
                is Resource.Success -> {
                    savedStateHandle["isLoading"] = false
                    savedStateHandle["error"] = ""
                    savedStateHandle["data"] = res.data
                }
                is Resource.Error -> {
                    savedStateHandle["isLoading"] = false
                    savedStateHandle["error"] = res.message
                }
            }

        }.launchIn(viewModelScope)

    }



}

