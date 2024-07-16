package com.depotato.data.mapper

import com.depotato.domain.equipment.Equipment

class EquipmentMapper() {
    fun equipmentToSerializedEquipment(equipment: Equipment): com.depotato.data.remote.model.Equipment {
        return com.depotato.data.remote.model.Equipment(
            id = equipment.id,
            name = equipment.name,
            category = equipment.category,
            currentAmount = equipment.currentAmount,
            maxAmount = equipment.maxAmount,
            imageUrl = equipment.imageUrl)
    }
}