package com.example.lrsplayer.data.local.database_service.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lrsplayer.data.local.database_service.entity.PlaylistEntity

@Dao
interface PlaylistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlistEntity: PlaylistEntity)

    @Query("SELECT * FROM PLAYLISTENTITY")
    suspend fun getPlaylists():List<PlaylistEntity>

    @Query("SELECT * FROM PLAYLISTENTITY WHERE id = :id")
    suspend fun getPlaylistById(id:Int):PlaylistEntity

    @Delete
    suspend fun deletePlaylist(playlistEntity: PlaylistEntity)

}