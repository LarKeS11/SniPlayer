package com.example.lrsplayer.data.local.database_service.service

import androidx.lifecycle.LiveData
import com.example.lrsplayer.data.local.database_service.entity.MusicEntity

interface DatabaseServiceInterface {

    suspend fun insertMusic(musicEntity: MusicEntity)
    suspend fun getAllMusic():List<MusicEntity>

}