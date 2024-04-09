package com.depotato.jubjub_manager.di.module

import com.depotato.jubjub_manager.data.local.SharedPref
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory { SharedPref(androidContext()) }
}