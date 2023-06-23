package com.example.lrsplayer.data.local.database_service.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lrsplayer.data.local.database_service.entity.PlaylistEntity

@Dao
interface PlaylistDao {

    @Insert
    suspend fun insertPlaylist(playlistEntity: PlaylistEntity)

    @Query("SELECT * FROM PLAYLISTENTITY")
    suspend fun getPlaylists():List<PlaylistEntity>

}