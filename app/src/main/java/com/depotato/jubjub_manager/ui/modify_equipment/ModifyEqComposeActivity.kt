package com.depotato.jubjub_manager.ui.modify_equipment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depotato.jubjub_manager.function_module.UriConverter
import com.depotato.jubjub_manager.view.modify_equipment.ModifyEquipmentViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class ModifyEqComposeActivity<VM: ModifyEquipmentViewModel>
    : ComponentActivity() {

    abstract val viewModel: VM

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

        collectWhenStarted(viewModel.toastMessage){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        collectWhenStarted(viewModel.equipmentImageUri){
            if(it != Uri.EMPTY){
                viewModel.equipmentImageFile = UriConverter().getFileFromUri(contentResolver, it)
            }
        }
    }

    inline fun <reified T> LifecycleOwner.collectWhenStarted(
        flow: Flow<T>, // 제네릭 타입으로 변경
        noinline collect: suspend (T) -> Unit // 타입 변경
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect(collect)
            }
        }
    }


}