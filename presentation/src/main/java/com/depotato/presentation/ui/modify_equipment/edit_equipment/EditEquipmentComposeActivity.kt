package com.depotato.presentation.ui.modify_equipment.edit_equipment

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.depotato.domain.equipment.Equipment
import com.depotato.presentation.ui.modify_equipment.ModifyEqComposeActivity
import com.depotato.presentation.ui.modify_equipment.ModifyEquipmentScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditEquipmentComposeActivity : ModifyEqComposeActivity<EditEquipmentViewModel>() {

    override val viewModel : EditEquipmentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEquipmentInfo()

        setContent {
            ModifyEquipmentScreen(
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

    private fun editEquipment(){
        viewModel.editEquipment()
    }
}