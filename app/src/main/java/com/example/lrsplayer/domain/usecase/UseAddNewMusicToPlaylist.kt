package com.example.lrsplayer.domain.usecase

import com.example.lrsplayer.domain.mapper.toPlaylist
import com.example.lrsplayer.domain.mapper.toPlaylistEntity
import com.example.lrsplayer.domain.repository.LocalRepository

class UseAddNewMusicToPlaylist(
    private val localRepository: LocalRepository
) {

    suspend fun execute(playlistId:Int, id:Int){

        val playlist = localRepository.getPlaylistById(playlistId).toPlaylist()
        playlist.musics.add(id)
        localRepository.insertPlaylist(playlist.toPlaylistEntity())

    }

}