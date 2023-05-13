package com.example.lrsplayer.di

import android.content.Context
import com.example.lrsplayer.data.firebase.DataFirebaseRepository
import com.example.lrsplayer.data.firebase.FirebaseService
import com.example.lrsplayer.data.local.DataLocalRepository
import com.example.lrsplayer.data.local.audio_service.AudioService
import com.example.lrsplayer.domain.repository.FirebaseRepository
import com.example.lrsplayer.domain.repository.LocalRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideSingInRequest(
        context: Context,
    ): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1060043079380-5k7e00pghiqv6eol7q76i0tq7a8d7886.apps.googleusercontent.com")
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    @Singleton
    @Provides
    fun provideFirebaseService(
        signInClient:GoogleSignInClient
    ):FirebaseService{
        return FirebaseService(
            signInClient = signInClient
        )
    }



    @Singleton
    @Provides
    fun provideFirebaseRepository(
        firebaseService: FirebaseService
    ):FirebaseRepository{
        return DataFirebaseRepository(firebaseService)
    }

    @Singleton
    @Provides
    fun provideAudioService(context: Context):AudioService{
        return AudioService(context = context)
    }

    @Singleton
    @Provides
    fun provideLocalRepository(
        audioService: AudioService
    ):LocalRepository{
        return DataLocalRepository(audioService)
    }

}