package com.example.lrsplayer.domain.usecase

import com.example.lrsplayer.domain.repository.LocalRepository

class UseEditPlaylistName(
    private val localRepository: LocalRepository
) {

    suspend fun execute(name:String, id:Int){

        val playlist = localRepository.getPlaylistById(id)
        playlist.name = name
        localRepository.insertPlaylist(playlist)

    }

}