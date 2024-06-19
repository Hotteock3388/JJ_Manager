package com.depotato.jubjub_manager.ui.modify_equipment.edit_equipment

import android.os.Bundle
import androidx.activity.compose.setContent
import com.depotato.jubjub_manager.ui.main.equipment_list.Equipment
import com.depotato.jubjub_manager.ui.modify_equipment.ModifyEqComposeActivity
import com.depotato.jubjub_manager.ui.modify_equipment.ModifyEqScreen
import org.koin.android.ext.android.inject

class EditEquipmentComposeActivity : ModifyEqComposeActivity<EditEquipmentViewModel>() {

    override val viewModel : EditEquipmentViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEquipmentInfo()

        setContent {
            ModifyEqScreen()
                .ModifyEquipmentScreen(
                    viewModel = viewModel,
                    openGallery = { resultLauncher.launch(gallery) },
                    buttonOnClick = { editEquipment() }
                )
        }

        collectWhenStarted(viewModel.addComplete){
            finish()
        }

    }

    private fun initEquipmentInfo(){
        try {
            val equipment = intent.getSerializableExtra("equipment") as Equipment
            viewModel.initEquipmentData(equipment)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun editEquipment(){
        viewModel.editEquipment()
    }

}