package com.depotato.jubjub_manager.ui.modify_equipment.add_equipment

import androidx.lifecycle.viewModelScope
import com.depotato.jubjub_manager.domain.equipment.CommonResult
import com.depotato.jubjub_manager.domain.equipment.add.AddEquipmentUseCase
import com.depotato.jubjub_manager.domain.equipment.category.GetCategoriesUseCase
import com.depotato.jubjub_manager.ui.modify_equipment.ModifyEquipmentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEquipmentViewModel @Inject constructor (
    private val addEquipmentUseCase: AddEquipmentUseCase,
    getCategoriesUseCase: GetCategoriesUseCase
) : ModifyEquipmentViewModel(getCategoriesUseCase, "AddEquipmentViewModel") {

    fun addEquipment() {
        if(isEquipmentDataValid()){
            viewModelScope.launch {
                addEquipmentUseCase(getImageMultipartFile(), getEquipmentRequestBody())
                    .collect {
                        when (it) {
                            is CommonResult.Success -> {
                                _addComplete.emit(Unit)
                                emitToastMessage(it.responseMessage)
                            }

                            is CommonResult.Failure -> {
                                emitToastMessage(it.errorMessage)
                            }
                        }
                    }
            }
        }
    }

}