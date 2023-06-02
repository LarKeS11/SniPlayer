package com.example.lrsplayer.domain.model

import android.graphics.Bitmap
import java.io.Serializable

class Music(
    val id:Int? = null,
    val name:String,
    val path:String,
    val author:String? = "",
    var duration:String? = ""
):Serializable