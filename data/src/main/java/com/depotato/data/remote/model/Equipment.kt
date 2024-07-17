package com.depotato.data.remote.model

import com.depotato.domain.equipment.Equipment
import com.google.gson.annotations.SerializedName

data class Equipment(
    override var id: Int = 0,
    @SerializedName("name") override var name: String,
    @SerializedName("category") override var category: String,
    @SerializedName("current_amount") override var currentAmount: Int,
    @SerializedName("max_amount") override var maxAmount: Int,
    @SerializedName("image_address") override var imageUrl: String
): Equipment
