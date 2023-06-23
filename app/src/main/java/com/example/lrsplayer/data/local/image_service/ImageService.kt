package com.example.lrsplayer.data.local.image_service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class ImageService(
    private val context: Context
):ImageServiceInstance {


    override fun getBitmapByBytesArray(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    override fun saveImage(uri: Uri, name: String) {
        val f = File(context.cacheDir, name)
        val os: OutputStream = BufferedOutputStream(FileOutputStream(f))
        var bitmap = convertUriToBitmap(uri)
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, os)
        os.close()
    }

    override fun getImageByName(name: String): File {
        return File("${context.cacheDir}/${name}")
    }

    override fun deleteFile(file: File) {
        file.delete()
    }

    private fun convertUriToBitmap(uri: Uri): Bitmap {
        var bitmap:Bitmap? = null

        bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media.getBitmap(context.contentResolver, uri)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }

        return bitmap!!
    }

}