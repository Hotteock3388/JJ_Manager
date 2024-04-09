package com.depotato.jubjub_manager.function_module

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import java.io.File

class UriConverter {

    fun getFileFromUri(contentResolver: ContentResolver, uri: Uri): File {
        var fp: String

        contentResolver.query(uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)!!
            .run {
                moveToFirst()
                fp = getString(0)
                close()
            }

        return File(fp)
    }
}