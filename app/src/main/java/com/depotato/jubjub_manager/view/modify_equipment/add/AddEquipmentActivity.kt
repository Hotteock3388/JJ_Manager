package com.depotato.jubjub_manager.view.modify_equipment.add

import com.depotato.jubjub_manager.R
import com.depotato.jubjub_manager.databinding.ActivityAddEquipmentBinding
import com.depotato.jubjub_manager.view.modify_equipment.ModifyEquipmentBaseActivity
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