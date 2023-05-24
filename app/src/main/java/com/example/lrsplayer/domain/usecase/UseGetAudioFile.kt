package com.example.lrsplayer.domain.usecase

import android.media.MediaMetadataRetriever
import android.util.Log
import com.example.lrsplayer.domain.repository.LocalRepository
import java.io.File

class UseGetAudioFile(
    private val localRepository: LocalRepository
) {

    fun execute(name:String):File{
        val file  = localRepository.getAudioFile(name)
        return localRepository.getAudioFile(name)
    }

}

