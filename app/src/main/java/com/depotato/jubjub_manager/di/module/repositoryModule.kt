package com.depotato.jubjub_manager.di.module

import com.depotato.jubjub_manager.data.local.SharedPref
import com.depotato.jubjub_manager.domain.auth.AuthRepository
import com.depotato.jubjub_manager.domain.auth.AuthRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single { SharedPref(androidContext()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}