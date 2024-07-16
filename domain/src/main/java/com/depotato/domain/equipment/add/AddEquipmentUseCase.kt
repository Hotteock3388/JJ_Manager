package com.depotato.domain.equipment.add

import com.depotato.domain.equipment.CommonResult
import com.depotato.domain.equipment.Equipment
import com.depotato.domain.equipment.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class AddEquipmentUseCase @Inject constructor (
    private val equipmentRepository: EquipmentRepository
) {
    operator fun invoke(imageFile: MultipartBody.Part, equipment: Equipment): Flow<CommonResult> {
        return equipmentRepository.addEquipment(imageFile, equipment)
    }
}