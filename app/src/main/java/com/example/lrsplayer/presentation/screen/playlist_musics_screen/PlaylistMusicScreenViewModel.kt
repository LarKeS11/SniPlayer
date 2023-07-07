package com.example.lrsplayer.presentation.screen.playlist_musics_screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.domain.model.Playlist
import com.example.lrsplayer.domain.usecase.UseAddNewMusicToPlaylist
import com.example.lrsplayer.domain.usecase.UseDeletePlaylist
import com.example.lrsplayer.domain.usecase.UseDeletePlaylistMusic
import com.example.lrsplayer.domain.usecase.UseEditPlaylistName
import com.example.lrsplayer.domain.usecase.UseGetAllMusicFromLocalDatabase
import com.example.lrsplayer.domain.usecase.UseGetMusicImage
import com.example.lrsplayer.domain.usecase.UseGetPlaylistById
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
    private val useEditPlaylistName: UseEditPlaylistName,
    private val useGetPlaylistById: UseGetPlaylistById,
    private val useDeletePlaylistMusic: UseDeletePlaylistMusic,
    private val useDeletePlaylist: UseDeletePlaylist
):ViewModel() {

    private val _playlist = savedStateHandle.getStateFlow<Playlist?>("playlist", null)
    private val _isLoading = savedStateHandle.getStateFlow("isLoading", false)
    private val _error = savedStateHandle.getStateFlow("error","")

    val state = combine(_playlist, _isLoading, _error){
            _playlist, loading, error ->
        PlaylistMusicsScreenState(
            playlist = _playlist,
            isLoading = loading,
            error = error
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

    private val _selectedMusicsState = MutableStateFlow<List<Music>>(emptyList())
    val selectedMusicsState:StateFlow<List<Music>> = _selectedMusicsState

    private val _selectingModeState = MutableStateFlow(false)
    val selectingModeState:StateFlow<Boolean> = _selectingModeState

    init {
        observePlaylist()
    }

    private fun observePlaylist(){
        viewModelScope.launch {
            val playlist = useGetPlaylistById.execute(savedStateHandle.get<String>("playlistId")!!.toInt())
            savedStateHandle["playlist"] = playlist
            editPlaylistName(playlist.name)
        }
    }


    fun setSelectingMode(bool:Boolean){
        _selectingModeState.value = bool
    }
    fun selectNewMusic(music: Music){
        val lastList = selectedMusicsState.value.toMutableList()
        lastList.add(music)
        _selectedMusicsState.value = lastList.toList()
    }

    private fun cleanUpSelectedMusics(){
        val lastList = selectedMusicsState.value.toMutableList()
        lastList.clear()
        _selectedMusicsState.value = lastList.toList()
        setSelectingMode(false)
    }

    fun unSelectMusic(music: Music){
        val lastList = selectedMusicsState.value.toMutableList()
        lastList.remove(music)
        _selectedMusicsState.value = lastList.toList()
    }

    suspend fun deletePlaylist(){
        useDeletePlaylist.execute(state.value.playlist!!)
    }

    fun deleteAllSelectedMusics(
        observeMusicCallback:() -> Unit
    ){

        viewModelScope.launch {
            selectedMusicsState.value.forEach {
                useDeletePlaylistMusic.execute(
                    playlistId = savedStateHandle.get<String>("playlistId")!!.toInt(),
                    musicId = it.id!!
                )
            }
            cleanUpSelectedMusics()
            observeMusicCallback()
        }
    }

    fun getMusicImage(file: String) = useGetMusicImage.execute(file)

    fun getMusicsNotInPlaylist(musics:List<Music>){
        useGetAllMusicFromLocalDatabase.invoke().onEach { res ->
            when(res){
                is Resource.Loading -> {
                    savedStateHandle["isLoading"] = true
                }
                is Resource.Success -> {
                    savedStateHandle["isLoading"] = false
                    savedStateHandle["error"] = ""
                    _allMusics.clear()
                    res.data!!.filter {cur -> cur.id !in musics.map { it.id } }.forEach {
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

    fun addAllNewMusics(
        callbackObserveMusics:() -> Unit
    ){
        viewModelScope.launch {
            currentSelectedMusics.forEach {
                useAddNewMusicToPlaylist.execute(savedStateHandle.get<String>("playlistId")!!.toInt(), it)
            }
            resetSelectedMusic()
            callbackObserveMusics()
        }
    }

    fun changePlaylistName(){
        viewModelScope.launch {
            useEditPlaylistName.execute(
                name = _playlistName.value,
                id = savedStateHandle.get<String>("playlistId")!!.toInt()
            )
            switchEditMode()
            editPlaylistName(state.value.playlist!!.name)
            observePlaylist()
        }
    }

}