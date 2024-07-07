package com.depotato.jubjub_manager.di.module

import com.depotato.jubjub_manager.domain.equipment.add.AddEquipmentUseCase
import com.depotato.jubjub_manager.domain.equipment.category.GetCategoriesUseCase
import com.depotato.jubjub_manager.domain.equipment.edit.EditEquipmentUseCase
import org.koin.dsl.module


val useCaseModule = module {

    // Auth
//    factory { SignInUseCase(get()) }
//    factory { CheckLoginHistoryUseCase(get()) }

    // Equipment
//    factory { GetEquipmentsUseCase(get()) }
    factory { AddEquipmentUseCase(get()) }
    factory { EditEquipmentUseCase(get()) }

    // Category
    factory { GetCategoriesUseCase(get()) }


}