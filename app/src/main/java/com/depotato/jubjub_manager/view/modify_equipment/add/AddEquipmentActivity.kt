package com.depotato.jubjub_manager.view.modify_equipment.add

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.base.BaseActivity
import com.depotato.jubjub_manager.databinding.ActivityAddEquipmentBinding
import com.depotato.jubjub_manager.function_module.UriConverter
import com.depotato.jubjub_manager.view.CategorySpinnerAdapter
import com.depotato.jubjub_manager.view.modify_equipment.ModifyEquipmentBaseActivity
import com.depotato.jubjub_manager.view.modify_equipment.image.ImageAddState
import org.koin.android.ext.android.inject

class AddEquipmentActivity : ModifyEquipmentBaseActivity<ActivityAddEquipmentBinding, AddEquipmentViewModel>(R.layout.activity_add_equipment, "AddEquipmentActivity") {

    override val viewModel: AddEquipmentViewModel by inject()

    fun addEquipment(){
        //send EquipmentData
        if(viewModel.isEquipmentDataValid()){
            viewModel.addEquipment()
        }
    }

}