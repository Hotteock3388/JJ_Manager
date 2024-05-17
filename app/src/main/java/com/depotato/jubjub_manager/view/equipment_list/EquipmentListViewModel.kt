package com.depotato.jubjub_manager.view.equipment_list

import androidx.lifecycle.viewModelScope
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.domain.equipment.list.GetEquipmentsResult
import com.depotato.jubjub_manager.domain.equipment.list.GetEquipmentsUseCase
import com.depotato.jubjub_manager.view.equipment_list.adapter.Equipment
import com.depotato.jubjub_manager.view.equipment_list.adapter.EquipmentItemEventListener
import com.depotato.jubjub_manager.view.equipment_list.adapter.EquipmentListRVAdapter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EquipmentListViewModel(
    private val getEquipmentsUseCase: GetEquipmentsUseCase
): BaseViewModel("EquipmentListViewModel") {

    private val _equipmentsArray = MutableStateFlow<Array<Equipment>>(arrayOf())
    val equipmentsArray = _equipmentsArray.asStateFlow()

    val _searchText = MutableStateFlow<String>("")
    val searchText = _searchText.asStateFlow()

    private val _clickedEquipment = MutableSharedFlow<Equipment>()
    val clickedEquipment = _clickedEquipment.asSharedFlow()

    val event = object: EquipmentItemEventListener(){
        override fun onItemClick(equipment: Equipment) {
            viewModelScope.launch {
                _clickedEquipment.emit(equipment)
            }
        }
    }

    val adapter = EquipmentListRVAdapter(event)

    fun getEquipments(){
        viewModelScope.launch {
            getEquipmentsUseCase()
                .collect{
                    when (it) {
                        is GetEquipmentsResult.Success -> {
                            _equipmentsArray.value = it.equipments
                        }
                        is GetEquipmentsResult.Failure -> {
                            emitToastMessage(it.errorMessage)
                        }
                    }
                }
        }
    }

}