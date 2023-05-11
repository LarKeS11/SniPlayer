package com.example.lrsplayer.domain.usecase

import android.util.Log
import com.example.lrsplayer.until.Resource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class UseFirebaseSignUpWithLogin() {

    operator fun invoke(email:String, password:String):Flow<Resource<Boolean>> = callbackFlow {

        val auth = Firebase.auth

        trySend(Resource.Loading())
        Log.d("asdasdsad","it.message.toString()")
        if(email.isEmpty()) trySend(Resource.Error("empty email"))
        if(password.isEmpty()) trySend(Resource.Error("empty password"))
        if(password.isEmpty() || email.isEmpty()) {
            this.close()
        }
        if(password.isNotEmpty() && email.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful) trySend(Resource.Success(true))
            }.addOnFailureListener {
                Log.d("asdasdsad", it.message.toString())
                trySend(Resource.Error(it.message!!))
            }
        }

        awaitClose {  }

    }

}