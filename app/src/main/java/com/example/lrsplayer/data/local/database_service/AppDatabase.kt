package com.example.lrsplayer.data.local.database_service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lrsplayer.data.local.database_service.dao.MusicDao
import com.example.lrsplayer.data.local.database_service.entity.MusicEntity

@Database(entities = [MusicEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun musicDao():MusicDao

}