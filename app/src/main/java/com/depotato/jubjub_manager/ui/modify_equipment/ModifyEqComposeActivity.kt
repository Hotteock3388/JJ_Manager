package com.depotato.jubjub_manager.ui.modify_equipment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.depotato.jubjub_manager.base.BaseActivity
import com.depotato.jubjub_manager.function_module.UriConverter

abstract class ModifyEqComposeActivity<VM: ModifyEquipmentViewModel>
    : BaseActivity<VM>("ModifyEqComposeActivity") {


    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            try {
                viewModel.updateImageUri(result.data?.data!!)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        collectWhenStarted(viewModel.onNewImageSelected){
            viewModel.updateImageFile(UriConverter().getFileFromUri(contentResolver, viewModel.modifyEquipmentUiState.value.imageUri))
        }

    }

}