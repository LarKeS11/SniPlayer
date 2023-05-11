package com.example.lrsplayer.data.firebase

import com.example.lrsplayer.domain.repository.FirebaseRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class DataFirebaseRepository(
    private val firebaseService: FirebaseService
):FirebaseRepository {

    override fun getGoogleSignInClient(): GoogleSignInClient {
        return firebaseService.getSignInClient()
    }

    override fun getFirebaseAuthWithGoogle(idToken:String): Task<AuthResult> {
        return firebaseService.firebaseAuthWithGoogle(idToken)
    }

}