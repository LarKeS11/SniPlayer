package com.example.lrsplayer.domain.usecase

import android.util.Log
import com.example.lrsplayer.domain.mapper.toPlaylist
import com.example.lrsplayer.domain.model.Music
import com.example.lrsplayer.domain.repository.LocalRepository
import com.example.lrsplayer.until.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UseGetPlaylistMusics(
    private val localRepository: LocalRepository
) {

    operator fun invoke(playlistId:Int): Flow<Resource<List<Music>>> = flow {

        emit(Resource.Loading())
        try {
            val playlistMusics = localRepository.getPlaylistById(playlistId).toPlaylist().musics
            Log.d("sdfsdfddd",playlistMusics.toString())
            val data = localRepository.getAllMusicFromDatabase().map {
                val metadata = localRepository.getMusicMetadata(it.path)
                Music(
                    id = it.id,
                    name = it.name,
                    path = it.path,
                    author = metadata.author ?: "no author"
                )
            }.filter {mus -> mus.id!! in playlistMusics }
            emit(Resource.Success(data))
        }catch (e:Exception){
            emit(Resource.Error(e.message.toString()))
        }

    }

}