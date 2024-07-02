package com.depotato.jubjub_manager.ui.modify_equipment.edit_equipment

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.depotato.jubjub_manager.domain.equipment.CommonResult
import com.depotato.jubjub_manager.domain.equipment.category.GetCategoriesUseCase
import com.depotato.jubjub_manager.domain.equipment.edit.EditEquipmentUseCase
import com.depotato.jubjub_manager.ui.main.equipment_list.Equipment
import com.depotato.jubjub_manager.ui.modify_equipment.ModifyEquipmentViewModel
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
//            _equipmentImageUrl.value = equipment.imageUrl
            updateImageURL(equipment.imageUrl)
//            _equipmentName.value = equipment.name
            updateName(equipment.name)
//            _equipmentMaxAmount.value = equipment.maxAmount.toString()
            updateMaxAmount(equipment.maxAmount.toString())
//            _equipmentCurrentAmount.value = equipment.currentAmount.toString()
            updateCurrentAmount(equipment.currentAmount.toString())
            updateCategory(equipment.category)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun editEquipment() {
        if(isEquipmentDataValid()){
            if(modifyEquipmentUiState.value.imageUri == Uri.EMPTY){
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