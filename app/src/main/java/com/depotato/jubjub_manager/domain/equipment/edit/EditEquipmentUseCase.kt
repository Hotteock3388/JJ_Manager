package com.depotato.jubjub_manager.domain.equipment.edit

import com.depotato.jubjub_manager.domain.equipment.CommonResult
import com.depotato.jubjub_manager.domain.equipment.EquipmentRepository
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EditEquipmentUseCase(
    private val equipmentRepository: EquipmentRepository
) {

    fun includeImage(imageFile: MultipartBody.Part, equipment: RequestBody): Observable<CommonResult> {
        return equipmentRepository.editEquipmentIncludeImage(imageFile, equipment)
    }

    fun excludeImage(equipment: RequestBody): Observable<CommonResult> {
        return equipmentRepository.editEquipmentExcludeImage(equipment)
    }

}