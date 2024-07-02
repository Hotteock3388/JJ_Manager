package com.depotato.jubjub_manager.ui.modify_equipment

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class UploadEquipment(
    @SerializedName("name") var name: String,
    @SerializedName("category") var category: String,
    @SerializedName("current_amount") var currentAmount: Int,
    @SerializedName("max_amount") var maxAmount: Int
)