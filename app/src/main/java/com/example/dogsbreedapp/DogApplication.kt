package com.example.dogsbreedapp

import android.app.Application
import com.example.dogsbreedapp.di.daoModule
import com.example.dogsbreedapp.di.repoModule
import com.example.dogsbreedapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DogApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DogApplication)
            modules(daoModule)
            modules(viewModelModule)
            modules(repoModule)
        }
    }
}

