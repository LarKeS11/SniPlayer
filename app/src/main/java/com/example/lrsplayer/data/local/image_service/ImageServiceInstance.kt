package com.example.lrsplayer.data.local.image_service

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File

interface ImageServiceInstance {

    fun getBitmapByBytesArray(byteArray: ByteArray):Bitmap
    fun saveImage(uri:Uri, name:String)
    fun getImageByName(name:String):File
    fun deleteFile(file:File)


}