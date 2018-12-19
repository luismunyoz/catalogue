package com.luismunyoz.catalogue

import android.app.Activity
import android.app.Application
import com.luismunyoz.catalogue.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

}