package com.depotato.jubjub_manager.view.edit_equipment

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.function_module.SingleEventLiveData
import com.depotato.jubjub_manager.view.add_equipment.UploadEquipment
import java.io.File

class EditEquipmentViewModel: BaseViewModel("EditEquipmentViewModel") {


    val categoryArray = arrayOf(
        "카테고리를 선택하세요.", "패드 & 탭", "데스크탑", "노트북", "악세서리", "임베디드", "기타"
    )

    var imageUri = SingleEventLiveData<Uri>()

    var equipmentImage = File("")

    val equipmentName = MutableLiveData<String>()
//    val equipmentName = _equipmentName

    val equipmentMaxAmount = MutableLiveData<String>()
    val equipmentCurrentAmount = MutableLiveData<String>("")
//    val currentAmount = _currentAmount
    var equipmentCategory = ""

    val addComplete = SingleEventLiveData<Unit>()


    fun addEquipment(){
        // 서버에 Equipment 정보 전송
        val equipment = UploadEquipment(
            equipmentName.value!!,
            equipmentCategory,
            equipmentMaxAmount.value!!.toInt(),
            equipmentMaxAmount.value!!.toInt(),
            equipmentImage
        )
        _toastMessage.value = "$equipment"

        addComplete.value = Unit
    }

    fun isEquipmentDataValid(): Boolean {

        return if(imageUri.value == null){
            invalidData("기자재 사진을 등록해주세요")
        } else if(equipmentName.value!!.isBlank()){
            invalidData("기자재 이름을 입력해주세요.")
        } else if(equipmentMaxAmount.value!!.isBlank()){
            invalidData("기자재 전체 수량을 입력해주세요.")
        } else if(equipmentCurrentAmount.value!!.isBlank()){
            invalidData("기자재 잔여 수량을 입력해주세요.")
        } else if(equipmentCategory.isBlank()){
            invalidData("기자재 카테고리를 선택해주세요.")
        } else if(equipmentMaxAmount.value!!.toInt() < equipmentCurrentAmount.value!!.toInt()){
            invalidData("전체 수량이 잔여 수량보다 많아야 합니다.")
        } else{
            true
        }

    }

    private fun invalidData(msg: String): Boolean {
        _toastMessage.value = msg
        return false
    }

}