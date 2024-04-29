package com.depotato.jubjub_manager.domain.equipment

import io.reactivex.Observable

class GetEquipmentsUseCase(
    private val equipmentRepository: EquipmentRepository
) {

    operator fun invoke(): Observable<GetEquipmentsResult> {
        return equipmentRepository.getEquipments()
    }
}