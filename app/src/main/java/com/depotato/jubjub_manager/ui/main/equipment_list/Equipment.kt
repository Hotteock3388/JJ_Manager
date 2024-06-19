package com.depotato.jubjub_manager.ui.main.equipment_list

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Equipment(
    var id: Int = 0,
    @SerializedName("name") var name: String,
    @SerializedName("category") var category: String,
    @SerializedName("current_amount") var currentAmount: Int,
    @SerializedName("max_amount") var maxAmount: Int,
    @SerializedName("image_address") var imageUrl: String
): Serializable
