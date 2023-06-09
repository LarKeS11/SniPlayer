package com.example.lrsplayer.data.local.database_service.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.lrsplayer.data.local.database_service.entity.MusicEntity
import com.example.lrsplayer.domain.model.Music

@Dao
interface MusicDao {

    @Insert
    suspend fun insertMusic(musicEntity: MusicEntity)

    @Query("SELECT * FROM musicentity")
    suspend fun getAllMusic():List<MusicEntity>

    @Delete
    suspend fun deleteMusic(musicEntity: MusicEntity)

}