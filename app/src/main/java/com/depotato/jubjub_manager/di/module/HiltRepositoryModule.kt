package com.depotato.jubjub_manager.di.module

import com.depotato.jubjub_manager.domain.auth.AuthRepository
import com.depotato.jubjub_manager.domain.auth.AuthRepositoryImpl
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

}