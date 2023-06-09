package com.example.lrsplayer.data.local.audio_service

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.net.toFile
import androidx.core.net.toUri
import java.io.*


class AudioService(
    private val context: Context,
):AudioServiceInterface {
    @SuppressLint("Range")
    override fun saveAudioFile(audioUri: Uri): String {
        val inputStream = context.getContentResolver().openInputStream(audioUri)!!

        val filename = getFileName(audioUri)

        val outputFile = context.cacheDir.path + File.separator+filename+"_tmp.mp4"

        try {
            val f = File(outputFile)
            f.setWritable(true, false)
            val outputStream: OutputStream = FileOutputStream(f)
            val buffer = ByteArray(1024)
            var length = 0
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.close()
            inputStream.close()
            return filename
        } catch (e: IOException) {
            return filename
        }


    }

    private fun getBitmapByBytesArray(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    override fun getAudioFile(name: String):File {
        return File("${context.cacheDir}/${name}_tmp.mp4")
    }

    override fun getMusicMetadata(filepath: String): MusicMetadataEntity {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(filepath)
        val author = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
        val image = retriever.embeddedPicture
        return MusicMetadataEntity(
            author = author,
            image = if(image == null) null else getBitmapByBytesArray(image)
        )
    }

    override fun deleteFile(path: String) {
        val file = File(path)
        file.delete()
    }


    private fun getSongAuthorAndImage(file:String){
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(file)
        val author = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
        val image = retriever.embeddedPicture
    }

    @SuppressLint("Range")
    private fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor = context.getContentResolver().query(uri, null, null, null, null)!!
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

}