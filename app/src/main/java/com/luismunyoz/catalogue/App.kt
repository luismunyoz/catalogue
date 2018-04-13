package com.luismunyoz.catalogue

import android.app.Application
import com.luismunyoz.catalogue.di.ApplicationComponent
import com.luismunyoz.catalogue.di.ApplicationModule
import com.luismunyoz.catalogue.di.DaggerApplicationComponent

class App : Application() {

    companion object {
        lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    fun initializeDagger() {
        graph = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

}