package com.example.lrsplayer.domain.usecase

import com.example.lrsplayer.domain.repository.FirebaseRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class UseGetGoogleSignInClient(
    private val firebaseRepository: FirebaseRepository
) {

    fun execute(): GoogleSignInClient = firebaseRepository.getGoogleSignInClient()

}