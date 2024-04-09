package com.depotato.jubjub_manager.view.equipment_list

import androidx.lifecycle.MutableLiveData
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.function_module.SingleEventLiveData
import com.depotato.jubjub_manager.view.equipment_list.adapter.Equipment

class EquipmentListViewModel: BaseViewModel("EquipmentListViewModel") {

    var equipmentArray = arrayOfNulls<Equipment>(0)

    private val _updateEquipmentArrayCompete = SingleEventLiveData<Unit>()
    val updateEquipmentArrayComplete = _updateEquipmentArrayCompete

    val searchText = MutableLiveData<String>("")


    fun getEquipmentArray(){
        equipmentArray = arrayOf(
            Equipment("애플 iPad Pro 11형", "패드 & 탭", 14, 20, ""),
            Equipment("Macbook 13인치", "노트북", 2, 10, ""),
            Equipment("Magic Mouse 2", "악세서리", 10, 10, "")
        )

        _updateEquipmentArrayCompete.value = Unit
    }

}