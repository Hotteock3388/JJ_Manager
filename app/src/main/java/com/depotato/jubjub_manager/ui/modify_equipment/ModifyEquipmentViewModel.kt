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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


data class ModifyEquipmentUiState(
    var imageUri: Uri = Uri.EMPTY,
    var imageFile: File = File(""),
    var imageURL: String = "",
    var name: String = "",
    var maxAmount: String = "",
    var currentAmount: String = "",
    var categories: List<String> = listOf("카테고리를 선택하세요."),
    var category: String = categories[0]
)

open class ModifyEquipmentViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    className: String
) : BaseViewModel(className) {

    private val _modifyEquipmentUiState = MutableStateFlow(ModifyEquipmentUiState())
    val modifyEquipmentUiState = _modifyEquipmentUiState.asStateFlow()

    private val _onNewImageSelected = MutableSharedFlow<Unit>()
    val onNewImageSelected = _onNewImageSelected.asSharedFlow()

    private fun emitNewImageSelected() = viewModelScope.launch { _onNewImageSelected.emit(Unit) }
//    private val _categories = MutableStateFlow<List<String>>(listOf("카테고리를 선택해주세요."))
//    val categories = _categories.asStateFlow()

    var equipmentId = 0

//    val _equipmentImageUri = MutableStateFlow<Uri>(Uri.EMPTY)
//    val equipmentImageUri = _equipmentImageUri.asStateFlow()

    fun updateImageUri(value: Uri) {
//        viewModelScope.launch { _equipmentImageUri.emit(value) }
        viewModelScope.launch {
            _modifyEquipmentUiState.update {
                it.copy(imageUri = value)
            }
            if(value != Uri.EMPTY){
                emitNewImageSelected()
            }
        }
    }

//    var equipmentImageFile = File("")
    fun updateImageFile(value: File) {
        viewModelScope.launch {
            _modifyEquipmentUiState.update {
                it.copy(imageFile = value)
            }
        }
    }
    fun updateImageURL(value: String) {
        viewModelScope.launch {
            _modifyEquipmentUiState.update {
                it.copy(imageURL = value)
            }
        }
    }

//    val _equipmentImageUrl = MutableStateFlow<String>("")
//    val equipmentImageUrl = _equipmentImageUrl.asStateFlow()
//    fun updateImageUrl(value: String) {
//        viewModelScope.launch { _equipmentImageUrl.emit(value) }
//    }

//    val _equipmentName = MutableStateFlow<String>("")
//    val equipmentName = _equipmentName.asStateFlow()
    fun updateName(value: String) {
//        viewModelScope.launch { _equipmentName.emit(value) }
        viewModelScope.launch {
            _modifyEquipmentUiState.update {
                it.copy(name = value)
            }
        }
    }

//    val _equipmentMaxAmount = MutableStateFlow<String>("")
//    val equipmentMaxAmount = _equipmentMaxAmount.asStateFlow()
    fun updateMaxAmount(value: String){
//        viewModelScope.launch { _equipmentMaxAmount.emit(value) }
        viewModelScope.launch {
            _modifyEquipmentUiState.update {
                it.copy(maxAmount = value)
            }
        }
    }

//    val _equipmentCurrentAmount = MutableStateFlow<String>("")
//    val equipmentCurrentAmount = _equipmentCurrentAmount.asStateFlow()
    fun updateCurrentAmount(value: String){
//        viewModelScope.launch { _equipmentCurrentAmount.emit(value) }
        viewModelScope.launch {
            _modifyEquipmentUiState.update {
                it.copy(currentAmount = value)
            }
        }
    }

//    var __equipmentCategory = ""
//
//    var _equipmentCategory = MutableStateFlow<String>("카테고리를 선택하세요.")
//    val equipmentCategory = _equipmentCategory.asStateFlow()
    fun updateCategory(value: String){
        viewModelScope.launch {
//            _equipmentCategory.emit(value)
//            __equipmentCategory = value
            viewModelScope.launch {
                _modifyEquipmentUiState.update {
                    it.copy(category = value)
                }
            }
        }
    }
    fun updateCategories(value: List<String>){
        viewModelScope.launch {
            viewModelScope.launch {
                _modifyEquipmentUiState.update {
                    it.copy(categories = value)
                }
            }
        }
    }

    protected val _addComplete = MutableSharedFlow<Unit>()
    val addComplete = _addComplete.asSharedFlow()

    fun deleteImage(){
        if(modifyEquipmentUiState.value.imageUri == Uri.EMPTY){
            updateImageURL("")
        }else{
            updateImageUri(Uri.EMPTY)
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
                        updateCategories(listOf("카테고리를 선택하세요.").plus(it.categories))
//                        _categories.value = listOf("카테고리를 선택하세요.").plus(it.categories)
                    }
                    is GetCategoryResult.Failure -> {
                        emitToastMessage(it.errorMessage)
                    }
                }
            }
        }
    }

    fun getCategoryIdx(): Int {
        with(modifyEquipmentUiState.value){
            return categories.indexOf(category)
        }
    }

    fun getImageMultipartFile(): MultipartBody.Part {
        modifyEquipmentUiState.value.imageFile.let {
            return MultipartBody.Part.createFormData(
                "image",
                it.name,
                it.asRequestBody(
                    "image/${it.extension}".toMediaTypeOrNull()
                )
            )
        }
    }
    fun getEquipmentRequestBody(): RequestBody {
        return Gson().toJson(createEquipmentObject())
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    private fun createEquipmentObject(): Equipment {
        with(modifyEquipmentUiState.value){
            return Equipment(
                id = equipmentId,
                name = name,
                category = category,
                currentAmount = currentAmount.toInt(),
                maxAmount = maxAmount.toInt(),
                imageUrl = "" + imageURL
            )
        }
    }

    fun isEquipmentDataValid(): Boolean {
        with(modifyEquipmentUiState.value){
            return if (imageUri == Uri.EMPTY && imageURL.isBlank()) {
                invalidData("기자재 사진을 등록해주세요")
            } else if (name.isBlank()) {
                invalidData("기자재 이름을 입력해주세요.")
            } else if (maxAmount.isBlank()) {
                invalidData("기자재 전체 수량을 입력해주세요.")
            } else if (currentAmount.isBlank()) {
                invalidData("기자재 잔여 수량을 입력해주세요.")
            } else if (category.isBlank() || category == categories[0]) {
                invalidData("기자재 카테고리를 선택해주세요.")
            } else if (maxAmount.toInt() < currentAmount.toInt()) {
                invalidData("전체 수량이 잔여 수량보다 많아야 합니다.")
            } else {
                true
            }
        }
    }

    private fun invalidData(msg: String): Boolean {
        emitToastMessage(msg)
        return false
    }

}