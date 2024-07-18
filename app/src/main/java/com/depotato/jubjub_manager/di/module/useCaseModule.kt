package com.depotato.jubjub_manager.di.module

import com.depotato.jubjub_manager.domain.auth.login_hisotry.CheckLoginHistoryUseCase
import com.depotato.jubjub_manager.domain.auth.sign_in.SignInUseCase
import com.depotato.jubjub_manager.domain.equipment.add.AddEquipmentUseCase
import com.depotato.jubjub_manager.domain.equipment.category.GetCategoriesUseCase
import com.depotato.jubjub_manager.domain.equipment.edit.EditEquipmentUseCase
import com.depotato.jubjub_manager.domain.equipment.list.GetEquipmentsUseCase
import org.koin.dsl.module


val useCaseModule = module {

    // Auth
    factory { SignInUseCase(get()) }
    factory { CheckLoginHistoryUseCase(get()) }

    factory { GetEquipmentsUseCase(get()) }
    factory { AddEquipmentUseCase(get()) }
    factory { EditEquipmentUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }



}