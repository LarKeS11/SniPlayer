package com.example.lrsplayer.data.local.database_service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lrsplayer.data.local.database_service.dao.MusicDao
import com.example.lrsplayer.data.local.database_service.dao.PlaylistDao
import com.example.lrsplayer.data.local.database_service.entity.MusicEntity
import com.example.lrsplayer.data.local.database_service.entity.PlaylistEntity

@Database(entities = [MusicEntity::class, PlaylistEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun musicDao():MusicDao
    abstract fun playlistDao():PlaylistDao

}