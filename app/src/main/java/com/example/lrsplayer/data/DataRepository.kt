package com.example.lrsplayer.data

import com.example.lrsplayer.data.firebase.FirebaseService
import com.example.lrsplayer.domain.repository.Repository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class DataRepository(
   private val firebaseService: FirebaseService
):Repository {
    override fun getGoogleSignInClient(): GoogleSignInClient {
        return firebaseService.getSignInClient()
    }

    override fun getFirebaseAuthWithGoogle(idToken:String): Task<AuthResult> {
        return firebaseService.firebaseAuthWithGoogle(idToken)
    }


}