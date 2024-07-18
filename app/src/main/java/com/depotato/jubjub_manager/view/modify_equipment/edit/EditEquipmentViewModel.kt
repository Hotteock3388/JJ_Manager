package com.depotato.jubjub_manager.view.modify_equipment.edit

import com.depotato.jubjub_manager.domain.equipment.CommonResult
import com.depotato.jubjub_manager.domain.equipment.category.GetCategoriesUseCase
import com.depotato.jubjub_manager.domain.equipment.edit.EditEquipmentUseCase
import com.depotato.jubjub_manager.view.equipment_list.adapter.Equipment
import com.depotato.jubjub_manager.view.modify_equipment.ModifyEquipmentViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
            equipmentImageUrl.value = equipment.imageUrl
            equipmentName.value = equipment.name
            equipmentMaxAmount.value = equipment.maxAmount.toString()
            equipmentCurrentAmount.value = equipment.currentAmount.toString()
            equipmentCategory = equipment.category
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getCategoryIdx(): Int {
        return categories.indexOf(equipmentCategory)
    }

    fun editEquipment() {
        if(equipmentImageUri.value == null){
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

    private fun setHandler(observable: Observable<CommonResult>){
        addDisposable(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleEditEquipmentResult(it)
                },{
                    handleEditEquipmentError(it)
                })
        )
    }

    private fun handleEditEquipmentResult(result: CommonResult){
        when(result){
            is CommonResult.Success -> {
                _addComplete.value = Unit
                _toastMessage.value = result.responseMessage
            }
            is CommonResult.Failure -> {
                _toastMessage.value = result.errorMessage
            }
        }
    }

    private fun handleEditEquipmentError(error: Throwable){
        _toastMessage.value = error.localizedMessage
        error.printStackTrace()
    }

}