package com.depotato.jubjub_manager.domain.equipment.list

import com.depotato.jubjub_manager.domain.equipment.EquipmentRepository
import kotlinx.coroutines.flow.Flow

class GetEquipmentsUseCase(
    private val equipmentRepository: EquipmentRepository
) {

    suspend operator fun invoke(): Flow<GetEquipmentsResult> {
        return equipmentRepository.getEquipments()
    }
}