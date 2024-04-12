package com.depotato.jubjub_manager.view.modify_equipment.edit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.base.BaseActivity
import com.depotato.jubjub_manager.databinding.ActivityEditEquipmentBinding
import com.depotato.jubjub_manager.function_module.UriConverter
import com.depotato.jubjub_manager.view.CategorySpinnerAdapter
import com.depotato.jubjub_manager.view.modify_equipment.image.ImageAddState
import com.depotato.jubjub_manager.view.equipment_list.adapter.Equipment
import com.depotato.jubjub_manager.view.modify_equipment.ModifyEquipmentBaseActivity
import org.koin.android.ext.android.inject

class EditEquipmentActivity : ModifyEquipmentBaseActivity<ActivityEditEquipmentBinding, EditEquipmentViewModel>(R.layout.activity_edit_equipment, "EditEquipmentActivity") {

    override val viewModel: EditEquipmentViewModel by inject()

    override fun init() {
        super.init()
        initEquipmentInfo()
    }

    private fun initEquipmentInfo(){

        try {
            val equipmentInfo = intent.getSerializableExtra("equipment") as Equipment

            with(viewModel){
                equipmentName.value = equipmentInfo.name
                equipmentMaxAmount.value = equipmentInfo.maxAmount.toString()
                equipmentCurrentAmount.value = equipmentInfo.currentAmount.toString()
                equipmentCategory = equipmentInfo.category

                binding.spinnerCategory.setSelection(getCategoryIdx(equipmentInfo.category))
            }

        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun getCategoryIdx(category: String): Int {
        return viewModel.categoryArray.indexOf(category)
    }

    fun editEquipment(){
        //send EquipmentData

        if(viewModel.isEquipmentDataValid()){
            viewModel.editEquipment()
        }

    }
}