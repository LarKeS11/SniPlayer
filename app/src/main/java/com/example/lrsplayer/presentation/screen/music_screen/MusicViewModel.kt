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


    private val _musicHasUpdated = MutableStateFlow(false)
    val musicHasUpdated:StateFlow<Boolean> = _musicHasUpdated

    fun getMusicImage(file: String) = useGetMusicImage.execute(file)


    fun uploadMusics(musics:List<Music>){
        savedStateHandle["data"] = musics
    }



}

