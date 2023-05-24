package com.example.lrsplayer.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.lrsplayer.data.local.database_service.dao.MusicDao
import com.example.lrsplayer.data.local.database_service.entity.MusicEntity
import com.example.lrsplayer.domain.model.Music
import java.io.File

interface LocalRepository {

    fun saveAudioFile(uri:Uri):String
    fun getAudioFile(name:String):File

    suspend fun insertMusicToDatabase(music: MusicEntity)
    suspend fun getAllMusicFromDatabase():List<MusicEntity>

}