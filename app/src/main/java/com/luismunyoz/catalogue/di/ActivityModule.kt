package com.luismunyoz.catalogue.di

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.luismunyoz.catalogue.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
abstract class ActivityModule(protected val activity: AppCompatActivity) {

    @Provides @ActivityScope
    fun provideActivity(): AppCompatActivity = activity

    @Provides @ActivityScope
    fun provideActivityContext(): Context = activity.baseContext
}