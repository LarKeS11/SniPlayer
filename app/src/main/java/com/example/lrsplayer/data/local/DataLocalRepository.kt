package com.example.lrsplayer.data.local

import android.net.Uri
import com.example.lrsplayer.data.local.audio_service.AudioService
import com.example.lrsplayer.data.local.audio_service.MusicMetadataEntity
import com.example.lrsplayer.data.local.database_service.entity.MusicEntity
import com.example.lrsplayer.data.local.database_service.entity.PlaylistEntity
import com.example.lrsplayer.data.local.database_service.service.DatabaseService
import com.example.lrsplayer.data.local.ids_service.IdsService
import com.example.lrsplayer.data.local.image_service.ImageService
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.domain.repository.LocalRepository
import java.io.File

class DataLocalRepository(
    private val audioService: AudioService,
    private val databaseService: DatabaseService,
    private val imageService: ImageService,
    private val idsService: IdsService
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

    override fun getMusicMetadata(filepath:String): MusicMetadataEntity {
        return audioService.getMusicMetadata(filepath)
    }

    override suspend fun deleteMusic(music: MusicEntity) {
        databaseService.deleteMusic(music)
    }

    override suspend fun insertPlaylist(playlistEntity: PlaylistEntity) {
        databaseService.insertPlaylist(playlistEntity)
    }

    override suspend fun getPlaylists(): List<PlaylistEntity> {
        return databaseService.getPlaylists()
    }

    override suspend fun getPlaylistById(id: Int): PlaylistEntity {
        return databaseService.getPlaylistById(id)
    }

    override suspend fun deletePlaylist(playlistEntity: PlaylistEntity) {
        databaseService.deletePlaylist(playlistEntity)
    }

    override fun saveImage(name: String, uri: Uri) {
        imageService.saveImage(uri = uri, name = name)
    }

    override fun getImageByName(name: String): File {
        return imageService.getImageByName(name)
    }
}