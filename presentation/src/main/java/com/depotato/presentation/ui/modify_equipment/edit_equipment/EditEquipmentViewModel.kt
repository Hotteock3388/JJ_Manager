package com.depotato.presentation.ui.modify_equipment.edit_equipment

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.depotato.domain.equipment.CommonResult
import com.depotato.domain.equipment.Equipment
import com.depotato.domain.equipment.category.GetCategoriesUseCase
import com.depotato.domain.equipment.edit.EditEquipmentUseCase
import com.depotato.presentation.ui.modify_equipment.ModifyEquipmentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEquipmentViewModel @Inject constructor (
    getCategoriesUseCase: GetCategoriesUseCase,
    private val editEquipmentUseCase: EditEquipmentUseCase
) : ModifyEquipmentViewModel(
    getCategoriesUseCase,
    "EditEquipmentViewModel"
) {

    fun initEquipmentData(equipment: Equipment){
        try {
            equipmentId = equipment.id
            updateImageURL(equipment.imageUrl)
            updateName(equipment.name)
            updateMaxAmount(equipment.maxAmount.toString())
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
                        createEquipmentObject()
                    )
                )
            } else {
                setHandler(
                    editEquipmentUseCase.includeImage(
                        getImageMultipartFile(),
                        createEquipmentObject()
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
                    addComplete()
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