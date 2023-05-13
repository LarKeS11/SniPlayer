package com.example.lrsplayer.presentation.screen.sign_in_screen

data class SignInState(
    val login:String = "",
    val password:String = "",
    val isLoading:Boolean = false,
    val error:String = "",
    val isSuccess:Boolean = false
)