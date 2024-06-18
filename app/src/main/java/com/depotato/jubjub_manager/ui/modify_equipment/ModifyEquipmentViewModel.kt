package com.depotato.jubjub_manager.ui.modify_equipment

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.domain.equipment.GetCategoryResult
import com.depotato.jubjub_manager.domain.equipment.category.GetCategoriesUseCase
import com.depotato.jubjub_manager.ui.main.equipment_list.Equipment
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

open class ModifyEquipmentViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    className: String
) : BaseViewModel(className) {

    private val _categories = MutableStateFlow<List<String>>(listOf("카테고리를 선택해주세요."))
    val categories = _categories.asStateFlow()

    var equipmentId = 0

    val _equipmentImageUri = MutableStateFlow<Uri>(Uri.EMPTY)
    val equipmentImageUri = _equipmentImageUri.asStateFlow()
    fun updateImageUri(value: Uri) {
        viewModelScope.launch { _equipmentImageUri.emit(value) }
    }


    var equipmentImageFile = File("")

    val _equipmentImageUrl = MutableStateFlow<String>("")
    val equipmentImageUrl = _equipmentImageUrl.asStateFlow()
    fun updateImageUrl(value: String) {
        viewModelScope.launch { _equipmentImageUrl.emit(value) }
    }


    val _equipmentName = MutableStateFlow<String>("")
    val equipmentName = _equipmentName.asStateFlow()
    fun updateName(value: String) {
        viewModelScope.launch { _equipmentName.emit(value) }
    }


    val _equipmentMaxAmount = MutableStateFlow<String>("")
    val equipmentMaxAmount = _equipmentMaxAmount.asStateFlow()
    fun updateMaxAmount(value: String){
        viewModelScope.launch { _equipmentMaxAmount.emit(value) }
    }


    val _equipmentCurrentAmount = MutableStateFlow<String>("")
    val equipmentCurrentAmount = _equipmentCurrentAmount.asStateFlow()
    fun updateCurrentAmount(value: String){
        viewModelScope.launch { _equipmentCurrentAmount.emit(value) }
    }


    var __equipmentCategory = ""

    var _equipmentCategory = MutableStateFlow<String>("카테고리를 선택하세요.")
    val equipmentCategory = _equipmentCategory.asStateFlow()
    fun updateEquipmentCategory(value: String){
        viewModelScope.launch {
            _equipmentCategory.emit(value)
            __equipmentCategory = value
        }
    }

    protected val _addComplete = MutableSharedFlow<Unit>()
    val addComplete = _addComplete.asSharedFlow()

    fun deleteImage(){
        if(_equipmentImageUri.value == Uri.EMPTY){
            _equipmentImageUrl.value = ""
        }else{
            _equipmentImageUri.value = Uri.EMPTY
        }
    }

    init {
        getCategories()
    }

    fun getCategories(){
        viewModelScope.launch {
            getCategoriesUseCase().collect{
                when(it){
                    is GetCategoryResult.Success -> {
                        _categories.value = listOf("카테고리를 선택하세요.").plus(it.categories)
                    }
                    is GetCategoryResult.Failure -> {
                        emitToastMessage(it.errorMessage)
                    }
                }
            }
        }
    }

    fun getCategoryIdx(): Int {
        return categories.value.indexOf(__equipmentCategory)
    }

    fun getImageMultipartFile(): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            "image",
            equipmentImageFile.name,
            equipmentImageFile.asRequestBody(
                "image/${equipmentImageFile.extension}".toMediaTypeOrNull()
            )
        )
    }
    fun getEquipmentRequestBody(): RequestBody {
        return Gson().toJson(createEquipmentObject())
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun createEquipmentObject(): Equipment {
        return Equipment(
            id = equipmentId,
            name = _equipmentName.value,
            category = __equipmentCategory,
            currentAmount = equipmentCurrentAmount.value.toInt(),
            maxAmount = equipmentMaxAmount.value.toInt(),
            imageUrl = "" + equipmentImageUrl.value
        )
    }

    fun isEquipmentDataValid(): Boolean {
        return if (equipmentImageUri.value == Uri.EMPTY
            && equipmentImageUrl.value.isBlank()) {
            invalidData("기자재 사진을 등록해주세요")
        } else if (equipmentName.value.isBlank()) {
            invalidData("기자재 이름을 입력해주세요.")
        } else if (equipmentMaxAmount.value.isBlank()) {
            invalidData("기자재 전체 수량을 입력해주세요.")
        } else if (equipmentCurrentAmount.value.isBlank()) {
            invalidData("기자재 잔여 수량을 입력해주세요.")
        } else if (__equipmentCategory.isBlank() && equipmentCategory.value.isBlank() || equipmentCategory.value == "카테고리를 선택하세요.") {
            invalidData("기자재 카테고리를 선택해주세요.")
        } else if (equipmentMaxAmount.value.toInt() < equipmentCurrentAmount.value.toInt()) {
            invalidData("전체 수량이 잔여 수량보다 많아야 합니다.")
        } else {
            true
        }
    }

    private fun invalidData(msg: String): Boolean {
        emitToastMessage(msg)
        return false
    }

}