package com.depotato.jubjub_manager.di.module

import com.depotato.data.remote.api.auth.AuthRepositoryImpl
import com.depotato.data.remote.api.equipment.EquipmentRepositoryImpl
import com.depotato.domain.auth.AuthRepository
import com.depotato.domain.equipment.EquipmentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class HiltRepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository


    @Binds
    abstract fun bindEquipmentRepository(
        equipmentRepositoryImpl: EquipmentRepositoryImpl
    ): EquipmentRepository

}