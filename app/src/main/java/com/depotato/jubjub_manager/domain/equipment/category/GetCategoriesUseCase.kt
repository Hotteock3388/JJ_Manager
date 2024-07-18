package com.depotato.jubjub_manager.domain.equipment.category

import com.depotato.jubjub_manager.domain.equipment.EquipmentRepository
import com.depotato.jubjub_manager.domain.equipment.GetCategoryResult
import io.reactivex.Observable

class GetCategoriesUseCase(
    private val equipmentRepository: EquipmentRepository
) {
    operator fun invoke(): Observable<GetCategoryResult> {
        return equipmentRepository.getCategories()
    }
}