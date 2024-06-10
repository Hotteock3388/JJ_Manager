package com.depotato.jubjub_manager.view.equipment_list

import androidx.lifecycle.viewModelScope
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.domain.equipment.list.GetEquipmentsResult
import com.depotato.jubjub_manager.domain.equipment.list.GetEquipmentsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//class FakeEquipmentListViewModel(
//
//): EquipmentListViewModel

class EquipmentListViewModel(
    private val getEquipmentsUseCase: GetEquipmentsUseCase
): BaseViewModel("EquipmentListViewModel") {

    private val _equipments = MutableStateFlow<List<Equipment>>(listOf())
    val equipments = _equipments.asStateFlow()

    val _searchText = MutableStateFlow<String>("")
    val searchText = _searchText.asStateFlow()

    private val _clickedEquipment = MutableSharedFlow<Equipment>()
    val clickedEquipment = _clickedEquipment.asSharedFlow()

    init { getEquipments() }

    fun emitSearchText(text: String){
        viewModelScope.launch {
            _searchText.emit(text)
        }
    }

    fun emitClickedEquipment(equipment: Equipment){
        viewModelScope.launch {
            _clickedEquipment.emit(equipment)
        }
    }


    fun getEquipments(){
        viewModelScope.launch {
            getEquipmentsUseCase()
                .collect{
                    when (it) {
                        is GetEquipmentsResult.Success -> {
                            _equipments.value = it.equipments
                        }
                        is GetEquipmentsResult.Failure -> {
                            emitToastMessage(it.errorMessage)
                        }
                    }
                }
        }
    }

}