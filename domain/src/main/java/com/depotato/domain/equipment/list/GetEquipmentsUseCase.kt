package com.depotato.domain.equipment.list

import com.depotato.domain.equipment.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEquipmentsUseCase @Inject constructor(
    private val equipmentRepository: EquipmentRepository
) {
    operator fun invoke(): Flow<GetEquipmentsResult> {
        return equipmentRepository.getEquipments()
    }
}