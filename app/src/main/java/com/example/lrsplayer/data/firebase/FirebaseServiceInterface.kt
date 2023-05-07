package com.example.lrsplayer.data.firebase

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthResult

interface FirebaseServiceInterface {

    fun getSignInClient(): GoogleSignInClient
    fun firebaseAuthWithGoogle(idToken:String): com.google.android.gms.tasks.Task<AuthResult>

}