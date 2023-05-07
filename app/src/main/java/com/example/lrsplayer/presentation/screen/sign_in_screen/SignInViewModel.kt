package com.example.lrsplayer.presentation.screen.sign_in_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
):ViewModel() {

    private val _login = savedStateHandle.getStateFlow("login", "")
    private val _password = savedStateHandle.getStateFlow("pass","")

    val state = combine(_login, _password){
        login, pass ->
        SignInState(
            login = login,
            password = pass
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SignInState())

    fun changeLogin(login:String){
        savedStateHandle["login"] = login
    }

    fun changePassword(pass: String){
        savedStateHandle["pass"] = pass
    }

}