package com.example.lrsplayer.data.local.database_service.service

import androidx.lifecycle.LiveData
import com.example.lrsplayer.data.local.database_service.dao.MusicDao
import com.example.lrsplayer.data.local.database_service.dao.PlaylistDao
import com.example.lrsplayer.data.local.database_service.entity.MusicEntity
import com.example.lrsplayer.data.local.database_service.entity.PlaylistEntity

class DatabaseService(
    private val musicDao: MusicDao,
    private val playlistDao: PlaylistDao
):DatabaseServiceInterface {
    override suspend fun insertMusic(musicEntity: MusicEntity) {
        musicDao.insertMusic(musicEntity)
    }

    override suspend fun getAllMusic(): List<MusicEntity> {
        return musicDao.getAllMusic()
    }

    override suspend fun deleteMusic(musicEntity: MusicEntity) {
        musicDao.deleteMusic(musicEntity)
    }

    override suspend fun insertPlaylist(playlistEntity: PlaylistEntity) {
        playlistDao.insertPlaylist(playlistEntity)
    }

    override suspend fun getPlaylists(): List<PlaylistEntity> {
        return playlistDao.getPlaylists()
    }
}