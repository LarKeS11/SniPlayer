package com.example.lrsplayer.domain.model

import java.io.File
import java.io.Serializable

class Playlist(
    val id:Int? = null,
    val imgSrc:String?,
    var name:String,
    val musics:MutableList<Int>,
    val imageFile: File? = null
): Serializable