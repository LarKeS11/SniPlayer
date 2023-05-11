package com.example.lrsplayer.domain.usecase

import com.example.lrsplayer.domain.repository.FirebaseRepository
import com.example.lrsplayer.until.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class UseMakeGoogleFirebaseAuthRequest(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(idToken:String): Flow<Resource<Boolean>> = callbackFlow{

        firebaseRepository.getFirebaseAuthWithGoogle(idToken).addOnCompleteListener {
            trySend(Resource.Success(true))
            this.close()
        }

        awaitClose {}
    }

}