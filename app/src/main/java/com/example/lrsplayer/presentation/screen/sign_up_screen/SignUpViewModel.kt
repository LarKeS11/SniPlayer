package com.example.lrsplayer.presentation.screen.sign_up_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lrsplayer.domain.usecase.UseFirebaseSignUpWithLogin
import com.example.lrsplayer.domain.usecase.UseGetGoogleSignInClient
import com.example.lrsplayer.domain.usecase.UseMakeGoogleFirebaseAuthRequest
import com.example.lrsplayer.presentation.screen.sign_in_screen.SignInState
import com.example.lrsplayer.until.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useGetGoogleSignInClient: UseGetGoogleSignInClient,
    private val useMakeGoogleFirebaseAuthRequest: UseMakeGoogleFirebaseAuthRequest,
    private val useFirebaseSignUpWithLogin: UseFirebaseSignUpWithLogin
):ViewModel() {

    private val _login = savedStateHandle.getStateFlow("login", "")
    private val _password = savedStateHandle.getStateFlow("pass","")
    private val _name = savedStateHandle.getStateFlow("name","")
    private val _isLoading = savedStateHandle.getStateFlow("is_loading", false)
    private val _error = savedStateHandle.getStateFlow("error","")
    private val _isSuccess = savedStateHandle.getStateFlow("success",false)

    val state = combine(_login, _password, _name,  _isLoading, _error, _isSuccess){ props ->
        SignUpState(
            login = props[0] as String,
            password = props[1] as String,
            name = props[2] as String,
            isLoading = props[3] as Boolean,
            error = props[4] as String,
            isSuccess = props[5] as Boolean
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SignUpState())


    fun getSignInClient() = useGetGoogleSignInClient.execute()
    fun makeRequest(idToken:String){
        useMakeGoogleFirebaseAuthRequest.invoke(idToken).onEach {res ->

            when(res){
                is Resource.Success -> {
                    savedStateHandle["success"] = true
                }
            }

        }.launchIn(viewModelScope)
    }


    fun makeLoginAuthRequest(){
        Log.d("asdasdsad","it.message.toString()")
        useFirebaseSignUpWithLogin.invoke(_login.value, _password.value, _name.value).onEach {res ->
            Log.d("asdasdsad","it.message.toString()")
            when(res){
                is Resource.Loading -> {
                    savedStateHandle["is_loading"] = true
                }
                is Resource.Success -> {
                    savedStateHandle["is_loading"] = false
                    savedStateHandle["error"] = ""
                    savedStateHandle["success"] = true
                }
                is Resource.Error -> {
                    savedStateHandle["is_loading"] = false
                    savedStateHandle["error"] = res.message
                }
            }

        }.launchIn(viewModelScope)
    }

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