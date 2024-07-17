package com.depotato.presentation.ui.main.equipment_list

import androidx.lifecycle.viewModelScope
import com.depotato.domain.equipment.Equipment
import com.depotato.domain.equipment.list.GetEquipmentsResult
import com.depotato.domain.equipment.list.GetEquipmentsUseCase
import com.depotato.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class EquipmentListUiState(
    var equipments: List<Equipment> = listOf(),
    var searchText: String = ""
)

@HiltViewModel
class EquipmentListViewModel @Inject constructor(
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