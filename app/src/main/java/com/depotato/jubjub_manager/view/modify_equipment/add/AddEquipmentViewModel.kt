package com.depotato.jubjub_manager.view.modify_equipment.add

import androidx.lifecycle.viewModelScope
import com.depotato.jubjub_manager.domain.equipment.CommonResult
import com.depotato.jubjub_manager.domain.equipment.add.AddEquipmentUseCase
import com.depotato.jubjub_manager.domain.equipment.category.GetCategoriesUseCase
import com.depotato.jubjub_manager.view.modify_equipment.ModifyEquipmentViewModel
import kotlinx.coroutines.launch

class AddEquipmentViewModel(
    private val addEquipmentUseCase: AddEquipmentUseCase,
    getCategoriesUseCase: GetCategoriesUseCase
) : ModifyEquipmentViewModel(getCategoriesUseCase, "AddEquipmentViewModel") {

    fun addEquipment() {
        viewModelScope.launch {
            addEquipmentUseCase(getImageMultipartFile(), getEquipmentRequestBody())
                .collect {
                    when (it) {
                        is CommonResult.Success -> {
                            _addComplete.value = Unit
                            _toastMessage.value = it.responseMessage
                        }

                        is CommonResult.Failure -> {
                            _toastMessage.value = it.errorMessage
                        }
                    }
                }
        }
    }

}