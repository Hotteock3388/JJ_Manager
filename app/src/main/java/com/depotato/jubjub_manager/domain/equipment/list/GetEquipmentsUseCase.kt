package com.depotato.jubjub_manager.domain.equipment.list

import com.depotato.jubjub_manager.domain.equipment.EquipmentRepository
import io.reactivex.Observable

class GetEquipmentsUseCase(
    private val equipmentRepository: EquipmentRepository
) {

    operator fun invoke(): Observable<GetEquipmentsResult> {
        return equipmentRepository.getEquipments()
    }
}