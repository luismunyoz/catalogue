package com.luismunyoz.catalogue.di

import android.content.Context
import com.luismunyoz.catalogue.App
import com.luismunyoz.catalogue.di.qualifier.ApplicationQualifier
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: App) {

    @Provides @Singleton
    fun provideApplication(): App = app

    @Provides @Singleton @ApplicationQualifier
    fun provideApplicationContext(): Context = app

}