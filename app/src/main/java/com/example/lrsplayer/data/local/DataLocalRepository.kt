package com.example.lrsplayer.data.local

import android.net.Uri
import com.example.lrsplayer.data.local.audio_service.AudioService
import com.example.lrsplayer.domain.repository.LocalRepository
import java.io.File

class DataLocalRepository(
    private val audioService: AudioService
):LocalRepository {
    override fun getAudioFireFromUri(uri: Uri): File {
        return audioService.getAudioFireFromUri(uri)
    }
}