package com.depotato.jubjub_manager.view.modify_equipment.edit

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.function_module.SingleEventLiveData
import com.depotato.jubjub_manager.view.modify_equipment.ModifyEquipmentViewModel
import com.depotato.jubjub_manager.view.modify_equipment.UploadEquipment
import java.io.File

class EditEquipmentViewModel: ModifyEquipmentViewModel("EditEquipmentViewModel") {

    fun editEquipment(){
        // 서버에 Equipment 정보 전송
        val equipment = createEquipmentObject()

        _toastMessage.value = "$equipment"

        addComplete.value = Unit
    }

}