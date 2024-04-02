package com.depotato.jubjub_manager.di.app

import android.app.Application
import com.depotato.jubjub_manager.di.module.activityModule
import com.depotato.jubjub_manager.di.module.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                activityModule,
                repositoryModule
            )
        }
    }
}
