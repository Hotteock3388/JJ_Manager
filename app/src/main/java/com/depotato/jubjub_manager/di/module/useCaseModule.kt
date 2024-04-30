package com.depotato.jubjub_manager.di.module

import com.depotato.jubjub_manager.domain.auth.CheckLoginHistoryUseCase
import com.depotato.jubjub_manager.domain.auth.SignInUseCase
import com.depotato.jubjub_manager.domain.equipment.add.AddEquipmentUseCase
import com.depotato.jubjub_manager.domain.equipment.category.GetCategoriesUseCase
import com.depotato.jubjub_manager.domain.equipment.list.GetEquipmentsUseCase
import org.koin.dsl.module


val useCaseModule = module {

    // Auth
    factory { SignInUseCase(get()) }
    factory { CheckLoginHistoryUseCase(get()) }

    factory { GetEquipmentsUseCase(get()) }
    factory { AddEquipmentUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }



}