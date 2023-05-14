package com.example.lrsplayer.data.local.database_service.service

import androidx.lifecycle.LiveData
import com.example.lrsplayer.data.local.database_service.dao.MusicDao
import com.example.lrsplayer.data.local.database_service.entity.MusicEntity

class DatabaseService(
    private val musicDao: MusicDao
):DatabaseServiceInterface {
    override suspend fun insertMusic(musicEntity: MusicEntity) {
        musicDao.insertMusic(musicEntity)
    }

    override suspend fun getAllMusic(): List<MusicEntity> {
        return musicDao.getAllMusic()
    }
}