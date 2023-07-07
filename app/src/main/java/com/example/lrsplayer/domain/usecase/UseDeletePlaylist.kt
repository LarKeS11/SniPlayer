package com.example.lrsplayer.domain.usecase

import com.example.lrsplayer.domain.mapper.toPlaylistEntity
import com.example.lrsplayer.domain.model.Playlist
import com.example.lrsplayer.domain.repository.LocalRepository

class UseDeletePlaylist(
    private val localRepository: LocalRepository
) {

    suspend fun execute(playlist:Playlist){
        localRepository.deletePlaylist(playlist.toPlaylistEntity())
    }

}