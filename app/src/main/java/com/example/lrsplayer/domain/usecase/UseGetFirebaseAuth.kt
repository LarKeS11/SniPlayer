package com.example.lrsplayer.domain.usecase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class UseGetFirebaseAuth() {

    fun execute():FirebaseAuth{
        return Firebase.auth
    }

}