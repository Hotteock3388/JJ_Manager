package com.depotato.domain.equipment.category

import com.depotato.domain.equipment.EquipmentRepository
import com.depotato.domain.equipment.GetCategoryResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor (
    private val equipmentRepository: EquipmentRepository
) {

    operator fun invoke(): Flow<GetCategoryResult> {
        return equipmentRepository.getCategories()
    }

}