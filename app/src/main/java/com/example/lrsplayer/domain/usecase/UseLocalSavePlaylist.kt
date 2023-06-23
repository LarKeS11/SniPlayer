package com.example.lrsplayer.domain.usecase

import com.example.lrsplayer.domain.mapper.toPlaylistEntity
import com.example.lrsplayer.domain.model.Playlist
import com.example.lrsplayer.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow

class UseLocalSavePlaylist(
    private val localRepository: LocalRepository
) {

    suspend fun execute(playlist: Playlist) {
        localRepository.insertPlaylist(playlist.toPlaylistEntity())
    }

}