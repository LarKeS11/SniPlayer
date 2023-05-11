package com.example.lrsplayer.di

import com.example.lrsplayer.domain.repository.FirebaseRepository
import com.example.lrsplayer.domain.usecase.UseFirebaseSignUpWithLogin
import com.example.lrsplayer.domain.usecase.UseGetFirebaseAuth
import com.example.lrsplayer.domain.usecase.UseGetGoogleSignInClient
import com.example.lrsplayer.domain.usecase.UseMakeGoogleFirebaseAuthRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideUseGetGoogleSignInClient(
        firebaseRepository: FirebaseRepository
    ):UseGetGoogleSignInClient{
        return UseGetGoogleSignInClient(firebaseRepository)
    }

    @Provides
    fun provideUseMakeGoogleFirebaseAuthRequest(
        firebaseRepository: FirebaseRepository
    ):UseMakeGoogleFirebaseAuthRequest{
        return UseMakeGoogleFirebaseAuthRequest(firebaseRepository)
    }

    @Provides
    fun provideUseGetFirebaseAuth():UseGetFirebaseAuth{
        return UseGetFirebaseAuth()
    }

    @Provides
    fun provideUseFirebaseSignUpWithLogin():UseFirebaseSignUpWithLogin{
        return UseFirebaseSignUpWithLogin()
    }
}