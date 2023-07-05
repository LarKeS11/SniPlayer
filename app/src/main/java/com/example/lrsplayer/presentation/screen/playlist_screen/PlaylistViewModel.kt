package com.example.lrsplayer.presentation.screen.playlist_screen

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lrsplayer.domain.model.Playlist
import com.example.lrsplayer.domain.usecase.UseGetPlaylists
import com.example.lrsplayer.domain.usecase.UseInsertMusicToLocalDatabase
import com.example.lrsplayer.domain.usecase.UseLocalSaveImageByNameAndUri
import com.example.lrsplayer.domain.usecase.UseLocalSavePlaylist
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
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val useLocalSaveImageByNameAndUri: UseLocalSaveImageByNameAndUri,
    private val useGetPlaylists: UseGetPlaylists,
    private val useLocalSavePlaylist: UseLocalSavePlaylist,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {


    private val _playlists = savedStateHandle.getStateFlow("playlists", listOf<List<Playlist>>())
    private val _isLoading = savedStateHandle.getStateFlow("is_loading", false)
    private val _error = savedStateHandle.getStateFlow("error", "")

    val state = combine(_playlists, _isLoading, _error){
        playlists, loading, error ->
        PlayListScreenState(
            playlists = playlists,
            isLoading = loading,
            error = error
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PlayListScreenState())


    private val _dialogActive = MutableStateFlow(false)
    val dialogActive:StateFlow<Boolean> = _dialogActive



    fun divideByPairs(list:List<Playlist>):List<List<Playlist>>{
        return list.withIndex()
            .groupBy { it.index / 2 }
            .map { it.value.map { it.value } }
    }



}