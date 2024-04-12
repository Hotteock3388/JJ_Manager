package com.depotato.jubjub_manager.view.modify_equipment.edit

import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.databinding.ActivityEditEquipmentBinding
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