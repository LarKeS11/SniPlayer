package com.example.lrsplayer.domain.repository

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface Repository {

    fun getGoogleSignInClient():GoogleSignInClient
    fun getFirebaseAuthWithGoogle(idToken:String): Task<AuthResult>

}