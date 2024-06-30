package com.depotato.jubjub_manager.ui.modify_equipment.add_equipment

import android.os.Bundle
import androidx.activity.compose.setContent
import com.depotato.jubjub_manager.ui.modify_equipment.ModifyEqComposeActivity
import com.depotato.jubjub_manager.ui.modify_equipment.ModifyEquipmentScreen
import org.koin.android.ext.android.inject

class AddEquipmentComposeActivity: ModifyEqComposeActivity<AddEquipmentViewModel>() {

    override val viewModel : AddEquipmentViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ModifyEquipmentScreen(
                viewModel = viewModel,
                openGallery = { resultLauncher.launch(gallery) },
                buttonOnClick = { viewModel.addEquipment() }
            )
        }

        collectWhenStarted(viewModel.addComplete){
            finish()
        }

    }

}