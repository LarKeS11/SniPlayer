package com.example.lrsplayer.presentation.screen.playlist_musics_screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.domain.usecase.UseAddNewMusicToPlaylist
import com.example.lrsplayer.domain.usecase.UseGetAllMusicFromLocalDatabase
import com.example.lrsplayer.domain.usecase.UseGetMusicImage
import com.example.lrsplayer.domain.usecase.UseGetPlaylistMusics
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
class PlaylistMusicScreenViewModel @Inject constructor(
    private val useGetAllMusicFromLocalDatabase: UseGetAllMusicFromLocalDatabase,
    private val useGetMusicImage: UseGetMusicImage,
    private val savedStateHandle: SavedStateHandle,
    private val useAddNewMusicToPlaylist: UseAddNewMusicToPlaylist,
    private val useGetPlaylistMusics: UseGetPlaylistMusics
):ViewModel() {

    private val _isLoading = savedStateHandle.getStateFlow("isLoading", false)
    private val _data = savedStateHandle.getStateFlow("data", mutableListOf<Music>())
    private val _error = savedStateHandle.getStateFlow("error","")

    val state = combine(_isLoading, _data, _error){
        loading, data, error ->
        PlaylistMusicsScreenState(
            isLoading = loading,
            error = error,
            data = data
        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PlaylistMusicsScreenState())

    private val _editMode = MutableStateFlow(false)
    val editMode:StateFlow<Boolean> = _editMode

    private val _alertDialogMode = MutableStateFlow(false)
    val alertDialogMode:StateFlow<Boolean> = _alertDialogMode

    private val _playlistName = MutableStateFlow("")
    val playlistName:StateFlow<String> = _playlistName

    private val _currentSelectedMusics = mutableStateListOf<Int>()
    val currentSelectedMusics = _currentSelectedMusics

    private val _allMusics = mutableStateListOf<Music>()
    val allMusics = _allMusics

    init {
        getMusics()
    }

    fun getMusicImage(file: String) = useGetMusicImage.execute(file)

    private fun getMusics(){
        useGetPlaylistMusics.invoke(savedStateHandle.get<String>("playlistId")!!.toInt()).onEach { res ->
            when(res){
                is Resource.Loading -> {
                    savedStateHandle["isLoading"] = true
                }
                is Resource.Success -> {
                    savedStateHandle["isLoading"] = false
                    savedStateHandle["error"] = ""
                    savedStateHandle["data"] = res.data
                    getMusicsNotInPlaylist()
                }
                is Resource.Error -> {
                    savedStateHandle["error"] = res.message
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMusicsNotInPlaylist(){
        useGetAllMusicFromLocalDatabase.invoke().onEach { res ->
            when(res){
                is Resource.Loading -> {
                    savedStateHandle["isLoading"] = true
                }
                is Resource.Success -> {
                    savedStateHandle["isLoading"] = false
                    savedStateHandle["error"] = ""
                    _allMusics.clear()
                    res.data!!.filter {cur -> cur.id !in _data.value.map { it.id } }.forEach {
                        _allMusics.add(it)
                    }
                }
                is Resource.Error -> {
                    savedStateHandle["error"] = res.message
                }
            }
        }.launchIn(viewModelScope)
    }

    fun switchEditMode(){
        _editMode.value = !_editMode.value
    }

    fun editPlaylistName(name:String){
        _playlistName.value = name
    }

    fun switchAlertDialogMode(bool:Boolean){
        _alertDialogMode.value = bool
    }

    fun addNewPlaylistMusic(id:Int){
        _currentSelectedMusics.add(id)
    }

    fun removePlaylistMusic(id:Int){
        _currentSelectedMusics.remove(id)
    }

    fun resetSelectedMusic(){
        _currentSelectedMusics.clear()
    }

    fun addAllNewMusics(){
        viewModelScope.launch {
            currentSelectedMusics.forEach {
                Log.d("sdfsdfsdfsdf",it.toString())
                useAddNewMusicToPlaylist.execute(savedStateHandle.get<String>("playlistId")!!.toInt(), it)
            }
            getMusics()
            resetSelectedMusic()
        }
    }

}