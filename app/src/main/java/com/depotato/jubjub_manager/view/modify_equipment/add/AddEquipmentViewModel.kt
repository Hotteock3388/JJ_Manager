package com.depotato.jubjub_manager.view.modify_equipment.add

import android.net.Uri
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.function_module.SingleEventLiveData
import com.depotato.jubjub_manager.view.modify_equipment.ModifyEquipmentViewModel
import com.depotato.jubjub_manager.view.modify_equipment.UploadEquipment
import java.io.File

class AddEquipmentViewModel: ModifyEquipmentViewModel("AddEquipmentViewModel") {

    fun addEquipment() {
        // 서버에 Equipment 정보 전송
        val equipment = createEquipmentObject()
        _toastMessage.value = "$equipment"

        addComplete.value = Unit
    }

}