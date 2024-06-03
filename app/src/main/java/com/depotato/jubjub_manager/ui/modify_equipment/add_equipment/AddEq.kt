package com.depotato.jubjub_manager.ui.modify_equipment.add_equipment

import android.os.Bundle
import androidx.activity.compose.setContent
import com.depotato.jubjub_manager.ui.modify_equipment.ModifyEqComposeActivity
import com.depotato.jubjub_manager.ui.modify_equipment.ModifyEquipmentScreenT
import com.depotato.jubjub_manager.view.modify_equipment.add.AddEquipmentViewModel
import org.koin.android.ext.android.inject

class AddEq: ModifyEqComposeActivity<AddEquipmentViewModel>() {

    override val viewModel : AddEquipmentViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ModifyEquipmentScreenT()
                .ModifyEquipmentScreen(
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