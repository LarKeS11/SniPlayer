package com.example.lrsplayer.presentation.screen.sign_up_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lrsplayer.presentation.screen.sign_in_screen.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
   private val savedStateHandle: SavedStateHandle
):ViewModel() {

    private val _login = savedStateHandle.getStateFlow("login", "")
    private val _password = savedStateHandle.getStateFlow("pass","")
    private val _name = savedStateHandle.getStateFlow("name","")

    val state = combine(_login, _password, _name){
            login, pass, name ->
        SignUpState(
            login = login,
            password = pass,
            name = name
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SignUpState())

    fun changeLogin(login:String){
        savedStateHandle["login"] = login
    }

    fun changePassword(pass: String){
        savedStateHandle["pass"] = pass
    }

    fun changeName(name:String){
        savedStateHandle["name"] = name
    }


}