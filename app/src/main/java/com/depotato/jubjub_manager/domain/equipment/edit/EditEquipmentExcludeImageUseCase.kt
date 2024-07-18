package com.depotato.jubjub_manager.domain.equipment.edit

import com.depotato.jubjub_manager.domain.equipment.CommonResult
import com.depotato.jubjub_manager.domain.equipment.EquipmentRepository
import io.reactivex.Observable
import okhttp3.RequestBody

class EditEquipmentExcludeImageUseCase(
    private val equipmentRepository: EquipmentRepository
) {
    operator fun invoke(equipment: RequestBody): Observable<CommonResult> {
        return equipmentRepository.editEquipmentExcludeImage(equipment)
    }
}