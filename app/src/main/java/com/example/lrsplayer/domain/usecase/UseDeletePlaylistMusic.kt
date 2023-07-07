package com.example.lrsplayer.domain.usecase

import com.example.lrsplayer.domain.mapper.toPlaylist
import com.example.lrsplayer.domain.mapper.toPlaylistEntity
import com.example.lrsplayer.domain.repository.LocalRepository

class UseDeletePlaylistMusic(
    private val localRepository: LocalRepository
) {

    suspend fun execute(musicId:Int, playlistId:Int){
        val playlist = localRepository.getPlaylistById(playlistId).toPlaylist()
        playlist.musics.remove(musicId)
        localRepository.insertPlaylist(playlist.toPlaylistEntity())
    }

}