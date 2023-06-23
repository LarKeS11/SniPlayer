package com.example.lrsplayer.domain.model

import java.io.File
import java.io.Serializable

class Playlist(
    val id:Int? = null,
    val imgSrc:String?,
    val name:String,
    val musics:List<Int>,
    val imageFile: File? = null
): Serializable