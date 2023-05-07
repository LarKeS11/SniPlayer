package com.example.lrsplayer.di

import android.content.Context
import com.example.lrsplayer.R
import com.example.lrsplayer.data.DataRepository
import com.example.lrsplayer.data.firebase.FirebaseService
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
            .requestIdToken(context.getString(R.string.default_web_client_id))
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
    fun provideDataRepository(
        firebaseService: FirebaseService
    ): DataRepository{
        return DataRepository(
            firebaseService = firebaseService
        )
    }

}