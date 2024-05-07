package com.depotato.jubjub_manager.view.modify_equipment.add

import com.depotato.jubjub_manager.domain.equipment.CommonResult
import com.depotato.jubjub_manager.domain.equipment.add.AddEquipmentUseCase
import com.depotato.jubjub_manager.domain.equipment.category.GetCategoriesUseCase
import com.depotato.jubjub_manager.view.modify_equipment.ModifyEquipmentViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddEquipmentViewModel(
    private val addEquipmentUseCase: AddEquipmentUseCase,
    getCategoriesUseCase: GetCategoriesUseCase
): ModifyEquipmentViewModel(getCategoriesUseCase, "AddEquipmentViewModel") {

    fun addEquipment() {
        addDisposable(
            addEquipmentUseCase(getImageMultipartFile(), getEquipmentRequestBody())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                  when(it){
                      is CommonResult.Success -> {
                          _addComplete.value = Unit
                          _toastMessage.value = it.responseMessage
                      }
                      is CommonResult.Failure -> {
                          _toastMessage.value = it.errorMessage
                      }
                  }
                }, {
                    it.printStackTrace()
                    _toastMessage.value = it.localizedMessage
                })
        )

    }


}