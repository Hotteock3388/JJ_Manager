package com.depotato.jubjub_manager.domain.equipment.category

import com.depotato.jubjub_manager.domain.equipment.EquipmentRepository
import com.depotato.jubjub_manager.domain.equipment.GetCategoryResult
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase(
    private val equipmentRepository: EquipmentRepository
) {

    operator fun invoke(): Flow<GetCategoryResult> {
        return equipmentRepository.getCategories()
    }

}