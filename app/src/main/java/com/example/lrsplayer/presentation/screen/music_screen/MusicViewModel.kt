package com.example.lrsplayer.presentation.screen.music_screen

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.domain.usecase.*
import com.example.lrsplayer.until.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val useSaveAudioFile: UseSaveAudioFile,
    private val useGetAudioFile: UseGetAudioFile,
    private val useInsertMusicToLocalDatabase: UseInsertMusicToLocalDatabase,
    private val useGetAllMusicFromLocalDatabase: UseGetAllMusicFromLocalDatabase,
    private val savedStateHandle: SavedStateHandle,
    private val useGetMusicImage: UseGetMusicImage
):ViewModel() {

    private val _isLoading = savedStateHandle.getStateFlow("isLoading", false)
    private val _data = savedStateHandle.getStateFlow("data", listOf<Music>())
    private val _error = savedStateHandle.getStateFlow("error","")
    private val _currentMusic = savedStateHandle.getStateFlow<Music?>("current_music", null)

    val state = combine(_isLoading, _data, _error, _currentMusic){
        isLoading, data, error, currentMusic ->
        MusicState(
            isLoading = isLoading,
            data = data,
            error = error,
            currentMusic = currentMusic
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MusicState())


    init {
        getMusic()
    }

    fun setCurrentMusic(music: Music?){
        savedStateHandle["current_music"] = music

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

