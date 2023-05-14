package com.example.lrsplayer.di

import com.example.lrsplayer.domain.repository.FirebaseRepository
import com.example.lrsplayer.domain.repository.LocalRepository
import com.example.lrsplayer.domain.usecase.*
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

    @Provides
    fun provideUseFirebaseSignInWithLogin():UseFirebaseSignInWithLogin{
        return UseFirebaseSignInWithLogin()
    }

    @Provides
    fun provideUseGetAudioFileFromUri(
        localRepository: LocalRepository
    ):UseSaveAudioFile{
        return UseSaveAudioFile(localRepository)
    }

    @Provides
    fun provideUseGetAudioFile(
        localRepository: LocalRepository
    ):UseGetAudioFile{
        return UseGetAudioFile(localRepository)
    }

    @Provides
    fun provideUseInsertMusicToLocalDatabase(
        localRepository: LocalRepository
    ): UseInsertMusicToLocalDatabase{
        return UseInsertMusicToLocalDatabase(localRepository = localRepository)
    }

    @Provides
    fun provideUseGetAllMusicFromLocalDatabase(
        localRepository: LocalRepository
    ):UseGetAllMusicFromLocalDatabase{
        return UseGetAllMusicFromLocalDatabase(localRepository = localRepository)
    }
}