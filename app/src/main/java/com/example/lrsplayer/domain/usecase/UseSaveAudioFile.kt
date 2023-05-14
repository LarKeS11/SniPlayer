package com.example.lrsplayer.domain.usecase

import android.net.Uri
import com.example.lrsplayer.domain.repository.LocalRepository
import java.io.File

class UseSaveAudioFile(
    private val localRepository: LocalRepository
) {

    fun execute(uri:Uri):String{
        return localRepository.saveAudioFile(uri)
    }

}