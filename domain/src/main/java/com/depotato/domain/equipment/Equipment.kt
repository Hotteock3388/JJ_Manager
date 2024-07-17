package com.depotato.domain.equipment

import java.io.Serializable

interface Equipment : Serializable {
    var id: Int
    var name: String
    var category: String
    var currentAmount: Int
    var maxAmount: Int
    var imageUrl: String
}
