package com.depotato.jubjub_manager.view.equipment_list.adapter

import java.io.Serializable

data class Equipment(
    var name: String,
    var category: String,
    var currentAmount: Int,
    var maxAmount: Int,
    var imageUrl: String
): Serializable
