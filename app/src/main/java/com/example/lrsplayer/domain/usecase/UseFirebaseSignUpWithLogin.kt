package com.example.lrsplayer.domain.usecase

import android.util.Log
import com.example.lrsplayer.until.Resource
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseUserMetadata
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class UseFirebaseSignUpWithLogin() {

    operator fun invoke(email:String, password:String, name:String):Flow<Resource<Boolean>> = callbackFlow {

        val auth = Firebase.auth

        trySend(Resource.Loading())
        Log.d("asdasdsad","it.message.toString()")
        if(email.isEmpty()) trySend(Resource.Error("empty email"))
        if(password.isEmpty()) trySend(Resource.Error("empty password"))
        if(password.isEmpty() || email.isEmpty()) {
            this.close()
        }
        if(password.isNotEmpty() && email.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password).addOnFailureListener {
                trySend(Resource.Error(it.message!!))
            }
            auth.addAuthStateListener {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                if(it.currentUser != null) {
                    it.currentUser!!.updateProfile(profileUpdates).addOnCompleteListener {
                        if(it.isSuccessful) trySend(Resource.Success(true))
                    }

                }
            }

        }

        awaitClose {  }

    }

}