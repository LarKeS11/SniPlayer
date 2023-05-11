package com.example.lrsplayer.presentation.screen.sign_up_screen

data class SignUpState(
    val login:String = "",
    val password:String = "",
    val name:String = "",
    val isLoading:Boolean = false,
    val error:String = "",
    val isSuccess:Boolean = false
)