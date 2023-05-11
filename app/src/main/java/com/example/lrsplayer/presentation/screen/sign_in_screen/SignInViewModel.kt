package com.example.lrsplayer.presentation.screen.sign_in_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val useMakeGoogleFirebaseAuthRequest: UseMakeGoogleFirebaseAuthRequest
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

    private val _hasBeenAuthorizated = MutableStateFlow(false)
    val hasBeenAuthorizated = _hasBeenAuthorizated

    fun getSignInClient() = useGetGoogleSignInClient.execute()


    fun makeRequest(idToken:String){
        useMakeGoogleFirebaseAuthRequest.invoke(idToken).onEach {res ->

            when(res){
                is Resource.Success -> {
                    _hasBeenAuthorizated.value = true
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