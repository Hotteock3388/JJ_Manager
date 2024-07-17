package com.depotato.domain.equipment.edit

import com.depotato.domain.equipment.CommonResult
import com.depotato.domain.equipment.Equipment
import com.depotato.domain.equipment.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class EditEquipmentUseCase @Inject constructor (
    private val equipmentRepository: EquipmentRepository
) {
    fun includeImage(imageFile: MultipartBody.Part, equipment: Equipment): Flow<CommonResult> {
        return equipmentRepository.editEquipmentIncludeImage(imageFile, equipment)
    }
    fun excludeImage(equipment: Equipment): Flow<CommonResult> {
        return equipmentRepository.editEquipmentExcludeImage(equipment)
    }
}