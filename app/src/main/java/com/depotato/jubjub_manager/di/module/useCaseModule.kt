package com.depotato.jubjub_manager.di.module

import com.depotato.jubjub_manager.domain.auth.CheckLoginHistoryUseCase
import com.depotato.jubjub_manager.domain.auth.SignInUseCase
import org.koin.dsl.module


val useCaseModule = module {
    factory { SignInUseCase(get()) }
    factory { CheckLoginHistoryUseCase(get()) }

}