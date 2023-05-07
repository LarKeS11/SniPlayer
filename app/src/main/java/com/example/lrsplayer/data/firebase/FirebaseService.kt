package com.example.lrsplayer.data.firebase

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseService(
    private val signInClient: GoogleSignInClient
):FirebaseServiceInterface {

    private val auth = Firebase.auth
    override fun getSignInClient(): GoogleSignInClient = signInClient

    override fun firebaseAuthWithGoogle(idToken:String):  com.google.android.gms.tasks.Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential)
    }


}