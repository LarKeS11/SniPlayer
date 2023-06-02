package com.example.lrsplayer.presentation.screen.music_screen

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.text.format.Time
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    private val _currentMusic = savedStateHandle.getStateFlow<Music?>("current_music", null)
    private val _musicPause = savedStateHandle.getStateFlow("music_pause",false)

    private val _musicPayer = MediaPlayer()

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


    private val _showControlScreen = mutableStateOf(false)
    val showControlScreen:State<Boolean> = _showControlScreen

    private val _durationState = mutableStateOf("0:00")
    val durationState:State<String> = _durationState

    init {
        getMusic()
    }

    fun setCurrentMusic(music: Music?){
        music?.duration = convertMillisecondsToNormalTime(_musicPayer.duration)
        savedStateHandle["current_music"] = music

    }

    fun playMusic(music: Music, context: Context){
        _musicPayer.setDataSource(context, music.path.toUri())
        _musicPayer.prepare()
        _musicPayer.start()
        Log.d("fdfgsdfdf", convertMillisecondsToNormalTime(_musicPayer.duration))
      //  _musicPayer.currentPosition

    }

    fun getMusicDuration() = convertMillisecondsToNormalTime(_musicPayer.duration)

    fun musicListener(){

    }

    private fun convertMillisecondsToNormalTime(total:Int):String{
        val hours = (total / (60 * 60 * 1000))
        val minutes = (total / (60 * 1000)) % 60
        val seconds = (total / 1000)  % 60
        var str = ""
        if(hours != 0){
            str += "$hours:"
        }
        str += "$minutes:"
        str += "$seconds"
        return str
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

