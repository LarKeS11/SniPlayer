package com.example.lrsplayer.domain.usecase

import com.example.lrsplayer.domain.mapper.toPlaylist
import com.example.lrsplayer.domain.model.Playlist
import com.example.lrsplayer.domain.repository.LocalRepository

class UseGetPlaylistById(
    private val localRepository: LocalRepository
) {

    suspend fun execute(id:Int):Playlist = localRepository.getPlaylistById(id).toPlaylist()

}