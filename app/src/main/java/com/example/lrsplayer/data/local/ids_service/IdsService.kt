package com.example.lrsplayer.data.local.ids_service

import android.content.Context

class IdsService(
    private val context: Context
) {

    private val sharedPreferences =
        context.getSharedPreferences("player_ids", Context.MODE_PRIVATE)

    fun putCurrentImageId(id:Int){
        sharedPreferences.edit().putInt("image_id",id).apply()
    }
    fun getCurrentImageId():Int = sharedPreferences.getInt("image_id",0)

}