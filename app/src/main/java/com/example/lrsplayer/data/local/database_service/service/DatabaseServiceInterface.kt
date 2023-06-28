package com.example.lrsplayer.data.local.database_service.service

import androidx.lifecycle.LiveData
import com.example.lrsplayer.data.local.database_service.entity.MusicEntity
import com.example.lrsplayer.data.local.database_service.entity.PlaylistEntity

interface DatabaseServiceInterface {

    suspend fun insertMusic(musicEntity: MusicEntity)
    suspend fun getAllMusic():List<MusicEntity>
    suspend fun deleteMusic(musicEntity: MusicEntity)


    suspend fun insertPlaylist(playlistEntity: PlaylistEntity)
    suspend fun getPlaylists():List<PlaylistEntity>
    suspend fun getPlaylistById(id:Int):PlaylistEntity

}