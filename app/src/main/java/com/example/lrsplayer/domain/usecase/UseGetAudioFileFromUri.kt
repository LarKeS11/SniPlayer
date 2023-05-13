package com.example.lrsplayer.domain.usecase

import android.net.Uri
import com.example.lrsplayer.domain.repository.LocalRepository
import java.io.File

class UseGetAudioFileFromUri(
    private val localRepository: LocalRepository
) {

    fun execute(uri:Uri):File{
        return localRepository.getAudioFireFromUri(uri)
    }

}