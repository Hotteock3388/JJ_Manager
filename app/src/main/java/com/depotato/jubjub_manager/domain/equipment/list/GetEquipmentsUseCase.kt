package com.depotato.jubjub_manager.domain.equipment.list

import com.depotato.jubjub_manager.domain.equipment.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEquipmentsUseCase @Inject constructor(
    private val equipmentRepository: EquipmentRepository
) {

    suspend operator fun invoke(): Flow<GetEquipmentsResult> {
        return equipmentRepository.getEquipments()
    }
}