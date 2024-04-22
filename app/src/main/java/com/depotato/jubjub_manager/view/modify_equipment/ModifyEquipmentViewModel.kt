package com.depotato.jubjub_manager.view.modify_equipment

import android.net.Uri
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import com.depotato.jubjub_manager.base.BaseViewModel
import com.depotato.jubjub_manager.function_module.SingleEventLiveData
import com.depotato.jubjub_manager.view.equipment_list.adapter.Equipment
import java.io.File

open class ModifyEquipmentViewModel(
    className: String
) : BaseViewModel(className) {

    val categoryArray = arrayOf(
        "카테고리를 선택하세요.", "패드 & 탭", "데스크탑", "노트북", "액세서리", "임베디드", "기타"
    )

    var equipmentId = 0
    var equipmentImageUri = SingleEventLiveData<Uri>()
    var equipmentImageUrl = SingleEventLiveData<String>()

    var equipmentImage = File("")

    val equipmentName = MutableLiveData<String>()

    val equipmentMaxAmount = MutableLiveData<String>()
    val equipmentCurrentAmount = MutableLiveData<String>("")

    var equipmentCategory = ""

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