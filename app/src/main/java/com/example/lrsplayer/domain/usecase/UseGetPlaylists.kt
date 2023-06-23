package com.example.lrsplayer.domain.usecase

import com.example.lrsplayer.domain.mapper.toPlaylist
import com.example.lrsplayer.domain.model.Playlist
import com.example.lrsplayer.domain.repository.LocalRepository
import com.example.lrsplayer.until.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class UseGetPlaylists(
   private val localRepository: LocalRepository
){
    operator fun invoke(): Flow<Resource<List<Playlist>>> = flow{
        emit(Resource.Loading())
        try {
            val data = localRepository.getPlaylists().map { it.toPlaylist(localRepository.getImageByName(it.imgSrc!!)) }
            emit(Resource.Success(data))
        }catch (e:Exception){
            emit(Resource.Error(e.message.toString()))
        }

    }
}