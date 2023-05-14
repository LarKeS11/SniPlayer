package com.example.lrsplayer.data.local

import android.net.Uri
import com.example.lrsplayer.data.local.audio_service.AudioService
import com.example.lrsplayer.data.local.database_service.entity.MusicEntity
import com.example.lrsplayer.data.local.database_service.service.DatabaseService
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.domain.repository.LocalRepository
import java.io.File

class DataLocalRepository(
    private val audioService: AudioService,
    private val databaseService: DatabaseService
):LocalRepository {
    override fun saveAudioFile(uri: Uri): String {
        return audioService.saveAudioFile(uri)
    }

    override fun getAudioFile(name:String): File {
        return audioService.getAudioFile(name)
    }

    override suspend fun insertMusicToDatabase(music: MusicEntity) {
        databaseService.insertMusic(music)
    }

    override suspend fun getAllMusicFromDatabase(): List<MusicEntity> {
        return databaseService.getAllMusic()
    }
}