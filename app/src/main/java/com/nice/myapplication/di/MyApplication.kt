package com.nice.app_ex.di

import android.app.Application
import com.nice.app_ex.data.di.repositoryModule
import com.nice.app_ex.data.di.retrofitModule
import com.nice.myapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            androidLogger(Level.DEBUG)
            // Android context
            androidContext(this@MyApplication)
            // modules
            modules(arrayListOf(retrofitModule,viewModelModule,repositoryModule))
        }
    }
}