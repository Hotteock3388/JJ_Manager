package com.depotato.jubjub_manager.view.modify_equipment

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.domain.equipment.GetCategoryResult
import com.depotato.jubjub_manager.domain.equipment.category.GetCategoriesUseCase
import com.depotato.jubjub_manager.function_module.SingleEventLiveData
import com.depotato.jubjub_manager.view.equipment_list.adapter.Equipment
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

open class ModifyEquipmentViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    className: String
) : BaseViewModel(className) {

    var categoryArray = arrayOf("")

    var equipmentId = 0


    var equipmentImageUrl = SingleEventLiveData<String>()
    var equipmentImageFile = File("")

    val equipmentName = MutableLiveData<String>()

    val equipmentMaxAmount = MutableLiveData<String>()
    val equipmentCurrentAmount = MutableLiveData<String>("")

    var equipmentCategory = ""

    var _equipmentImageUri = SingleEventLiveData<Uri>()
    var equipmentImageUri: LiveData<Uri> = _equipmentImageUri

    protected val _getCategoriesComplete = SingleEventLiveData<Unit>()
    val getCategoriesComplete: LiveData<Unit> = _getCategoriesComplete

    protected val _addComplete = SingleEventLiveData<Unit>()
    val addComplete: LiveData<Unit> = _addComplete


    fun getCategories(){
        addDisposable(
            getCategoriesUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when(it){
                        is GetCategoryResult.Success -> {
                            categoryArray = arrayOf("카테고리를 선택하세요.").plus(it.categories)
                            _getCategoriesComplete.value = Unit
                        }
                        is GetCategoryResult.Failure -> {
                            _toastMessage.value = it.errorMessage
                        }
                    }
                }, {
                    it.printStackTrace()
                    _toastMessage.value = it.localizedMessage
                })
        )
    }

    fun getImageMultipartFile(): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            "image",
            equipmentImageFile.name,
            RequestBody.create(
                "image/${equipmentImageFile.extension}".toMediaTypeOrNull(),
                equipmentImageFile
            )
        )
    }
    fun getEquipmentRequestBody(): RequestBody {
        return RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            Gson().toJson(createEquipmentObject())
        )
    }


    protected fun createEquipmentObject(): Equipment {
        return Equipment(
            equipmentId,
            equipmentName.value!!,
            equipmentCategory,
            equipmentMaxAmount.value!!.toInt(),
            equipmentMaxAmount.value!!.toInt(),
            if(equipmentImageUrl.value != null) equipmentImageUrl.value!! else ""
        )
    }

    fun isEquipmentDataValid(): Boolean {

        return if (equipmentImageUri.value == null && equipmentImageUrl.value!!.isBlank()) {
            invalidData("기자재 사진을 등록해주세요")
        } else if (equipmentName.value!!.isBlank()) {
            invalidData("기자재 이름을 입력해주세요.")
        } else if (equipmentMaxAmount.value!!.isBlank()) {
            invalidData("기자재 전체 수량을 입력해주세요.")
        } else if (equipmentCurrentAmount.value!!.isBlank()) {
            invalidData("기자재 잔여 수량을 입력해주세요.")
        } else if (equipmentCategory.isBlank()) {
            invalidData("기자재 카테고리를 선택해주세요.")
        } else if (equipmentMaxAmount.value!!.toInt() < equipmentCurrentAmount.value!!.toInt()) {
            invalidData("전체 수량이 잔여 수량보다 많아야 합니다.")
        } else {
            true
        }

    }

    private fun invalidData(msg: String): Boolean {
        _toastMessage.value = msg
        return false
    }

}