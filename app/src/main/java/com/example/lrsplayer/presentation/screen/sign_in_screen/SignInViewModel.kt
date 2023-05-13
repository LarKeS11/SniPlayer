package com.example.lrsplayer.presentation.screen.sign_in_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lrsplayer.domain.usecase.UseFirebaseSignInWithLogin
import com.example.lrsplayer.domain.usecase.UseGetGoogleSignInClient
import com.example.lrsplayer.domain.usecase.UseMakeGoogleFirebaseAuthRequest
import com.example.lrsplayer.until.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useGetGoogleSignInClient: UseGetGoogleSignInClient,
    private val useMakeGoogleFirebaseAuthRequest: UseMakeGoogleFirebaseAuthRequest,
    private val useFirebaseSignInWithLogin: UseFirebaseSignInWithLogin
):ViewModel() {

    private val _login = savedStateHandle.getStateFlow("login", "")
    private val _password = savedStateHandle.getStateFlow("pass","")
    private val _isLoading = savedStateHandle.getStateFlow("is_loading", false)
    private val _error = savedStateHandle.getStateFlow("error","")
    private val _isSuccess = savedStateHandle.getStateFlow("success",false)

    val state = combine(_login, _password, _isLoading, _error, _isSuccess){
        login, pass, isLoading, error, isSuccess ->
        SignInState(
            login = login,
            password = pass,
            isLoading = isLoading,
            error = error,
            isSuccess = isSuccess
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SignInState())

    fun getSignInClient() = useGetGoogleSignInClient.execute()


    fun makeLoginAuthRequest(){
        useFirebaseSignInWithLogin.invoke(_login.value, _password.value).onEach {res ->
            when(res){

                is Resource.Loading -> savedStateHandle["is_loading"] = true
                is Resource.Success -> {
                    savedStateHandle["is_loading"] = false
                    savedStateHandle["error"] = ""
                    savedStateHandle["success"] = res.data
                }
                is Resource.Error -> {
                    savedStateHandle["is_loading"] = false
                    savedStateHandle["error"] = res.message
                }

            }
        }.launchIn(viewModelScope)
    }

    fun makeRequest(idToken:String){
        useMakeGoogleFirebaseAuthRequest.invoke(idToken).onEach {res ->

            when(res){
                is Resource.Success -> {
                    savedStateHandle["success"] = true
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

}