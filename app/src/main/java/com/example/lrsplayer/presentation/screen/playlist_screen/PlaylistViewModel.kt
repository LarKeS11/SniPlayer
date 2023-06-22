package com.example.lrsplayer.presentation.screen.playlist_screen

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(): ViewModel() {

    private val _dialogActive = MutableStateFlow(false)
    val dialogActive:StateFlow<Boolean> = _dialogActive

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri = _selectedImageUri

    fun setImage(uri:Uri){
        _selectedImageUri.value = uri
    }

    fun setDialogActive(bool:Boolean){
        _dialogActive.value = bool
    }

}