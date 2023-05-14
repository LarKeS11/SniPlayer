package com.example.lrsplayer.domain.model

import java.io.Serializable

data class Music(
    val id:Int? = null,
    val name:String,
    val path:String
): Serializable