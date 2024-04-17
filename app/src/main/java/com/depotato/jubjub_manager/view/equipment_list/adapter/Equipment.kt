package com.depotato.jubjub_manager.view.equipment_list.adapter

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Equipment(
    var id: Int = 0,
    var name: String,
    var category: String,
    @SerializedName("current_amount") var currentAmount: Int,
    @SerializedName("max_amount") var maxAmount: Int,
    @SerializedName("image_address") var imageUrl: String
): Serializable
