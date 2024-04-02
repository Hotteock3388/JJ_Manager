package com.depotato.jubjub_manager.function_module

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.depotato.jubjub_manager.base.BaseActivity
import com.depotato.jubjub_manager.entity.singleton.Constants.GALLERY_REQUEST_CODE

class ImageManager: AppCompatActivity() {

    fun openGallery(){
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, GALLERY_REQUEST_CODE)
    }


}