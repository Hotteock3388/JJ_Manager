package com.depotato.jubjub_manager.view.add_equipment

import android.net.Uri
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.function_module.SingleEventLiveData
import com.depotato.jubjub_manager.view.equipment_list.adapter.Equipment
import java.io.File

class AddEquipmentViewModel: BaseViewModel("AddEquipmentViewModel") {

    val categoryArray = arrayOf(
        "카테고리를 선택하세요.", "탭 & 패드", "데스크탑", "노트북", "악세서리", "임베디드", "기타"
    )

    var imageUri = SingleEventLiveData<Uri>()


//    var equipment = Equipment("", "", 0, 0, "")
    var equipmentImage = File("")

    val equipmentName = MutableLiveData<String>("")
    val equipmentAmount = MutableLiveData<String>("")
    private var equipmentCategory = ""

    val addComplete = SingleEventLiveData<Unit>()

    fun onSpinnerItemSelected(parent: AdapterView<*>?, position: Int) {
        if (parent != null) {
            equipmentCategory = if(position == 0){
                ""
            }else{
                categoryArray[position]
            }
        }
    }


    fun addEquipment(){
        // 서버에 Equipment 정보 전송
        val equipment = UploadEquipment(
            equipmentName.value!!,
            equipmentCategory,
            equipmentAmount.value!!.toInt(),
            equipmentAmount.value!!.toInt(),
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
        } else if(equipmentAmount.value!!.isBlank()){
            invalidData("기자재 수량을 입력해주세요.")
        } else if(equipmentCategory.isBlank()){
            invalidData("기자재 카테고리를 선택해주세요.")
        } else{
            true
        }

    }

    fun invalidData(msg: String): Boolean {
        _toastMessage.value = msg
        return false
    }

}