package com.depotato.jubjub_manager.ui.main.equipment_list

import androidx.lifecycle.viewModelScope
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.domain.equipment.list.GetEquipmentsResult
import com.depotato.jubjub_manager.domain.equipment.list.GetEquipmentsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class EquipmentListUiState(
    var equipments: List<Equipment> = listOf(),
    var searchText: String = ""
)

class EquipmentListViewModel(
    private val getEquipmentsUseCase: GetEquipmentsUseCase
): BaseViewModel("EquipmentListViewModel") {

    private val _equipmentListUiState = MutableStateFlow(EquipmentListUiState())
    val equipmentListUiState = _equipmentListUiState.asStateFlow()

    private val _clickedEquipment = MutableSharedFlow<Equipment>()
    val clickedEquipment = _clickedEquipment.asSharedFlow()

    fun emitSearchText(text: String){
        viewModelScope.launch {
            _equipmentListUiState.update{
                it.copy(searchText = text)
            }
        }
    }
    private fun emitEquipments(equipments: List<Equipment>){
        viewModelScope.launch {
            _equipmentListUiState.update {
                it.copy(equipments = equipments)
            }
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
                            emitEquipments(it.equipments)
                        }
                        is GetEquipmentsResult.Failure -> {
                            emitToastMessage(it.errorMessage)
                        }
                    }
                }
        }
    }

}