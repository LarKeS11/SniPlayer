package com.example.lrsplayer.domain.usecase

import android.graphics.Bitmap
import com.example.lrsplayer.data.local.audio_service.MusicMetadataEntity
import com.example.lrsplayer.domain.repository.LocalRepository

class UseGetMusicImage(
    private val localRepository: LocalRepository
) {

    fun execute(file: String):Bitmap?{
        val meta = localRepository.getMusicMetadata(file)
        return meta.image
    }

}