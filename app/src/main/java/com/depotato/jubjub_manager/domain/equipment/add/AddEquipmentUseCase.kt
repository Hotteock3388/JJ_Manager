package com.depotato.jubjub_manager.domain.equipment.add

import com.depotato.jubjub_manager.domain.equipment.CommonResult
import com.depotato.jubjub_manager.domain.equipment.EquipmentRepository
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddEquipmentUseCase(
    private val equipmentRepository: EquipmentRepository
) {

    operator fun invoke(imageFile: MultipartBody.Part, equipment: RequestBody): Observable<CommonResult> {
        return equipmentRepository.addEquipment(imageFile, equipment)
    }

}