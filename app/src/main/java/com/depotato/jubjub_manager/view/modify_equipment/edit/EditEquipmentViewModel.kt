package com.depotato.jubjub_manager.view.modify_equipment.edit

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.depotato.jubjub_manager.domain.equipment.CommonResult
import com.depotato.jubjub_manager.domain.equipment.category.GetCategoriesUseCase
import com.depotato.jubjub_manager.domain.equipment.edit.EditEquipmentUseCase
import com.depotato.jubjub_manager.view.equipment_list.Equipment
import com.depotato.jubjub_manager.view.modify_equipment.ModifyEquipmentViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class EditEquipmentViewModel(
    getCategoriesUseCase: GetCategoriesUseCase,
    private val editEquipmentUseCase: EditEquipmentUseCase
) : ModifyEquipmentViewModel(
    getCategoriesUseCase,
    "EditEquipmentViewModel"
) {

    fun initEquipmentData(equipment: Equipment){
        try {
            equipmentId = equipment.id
            _equipmentImageUrl.value = equipment.imageUrl
            _equipmentName.value = equipment.name
            _equipmentMaxAmount.value = equipment.maxAmount.toString()
            _equipmentCurrentAmount.value = equipment.currentAmount.toString()
//            __equipmentCategory =
            updateEquipmentCategory(equipment.category)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun editEquipment() {
        if(equipmentImageUri.value == Uri.EMPTY){
            setHandler(
                editEquipmentUseCase.excludeImage(
                    getEquipmentRequestBody()
                )
            )
        } else {
            setHandler(
                editEquipmentUseCase.includeImage(
                    getImageMultipartFile(),
                    getEquipmentRequestBody()
                )
            )
        }
    }

    private fun setHandler(observable: Flow<CommonResult>){
        viewModelScope.launch{
            observable.collect{
                handleEditEquipmentResult(it)
            }
        }
    }

    private fun handleEditEquipmentResult(result: CommonResult){
        when(result){
            is CommonResult.Success -> {
                viewModelScope.launch {
                    _addComplete.emit(Unit)
                }
                emitToastMessage(result.responseMessage)
            }
            is CommonResult.Failure -> {
                emitToastMessage(result.errorMessage)
            }
        }
    }

    private fun handleEditEquipmentError(error: Throwable){
        emitToastMessage(error.localizedMessage)
        error.printStackTrace()
    }

}