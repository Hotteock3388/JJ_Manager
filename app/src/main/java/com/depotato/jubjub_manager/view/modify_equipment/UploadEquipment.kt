package com.depotato.jubjub_manager.view.modify_equipment

import java.io.File

data class UploadEquipment(
    var name: String,
    var category: String,
    var currentAmount: Int,
    var maxAmount: Int,
    var image: File
)
