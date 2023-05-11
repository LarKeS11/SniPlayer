package com.example.lrsplayer.presentation.screen.splash_screen

import androidx.lifecycle.ViewModel
import com.example.lrsplayer.domain.usecase.UseGetFirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useGetFirebaseAuth: UseGetFirebaseAuth
):ViewModel(){

    val firebaseAuth = useGetFirebaseAuth.execute()

}