package com.luismunyoz.catalogue.di

import android.app.Application
import android.content.Context
import com.luismunyoz.catalogue.App
import com.luismunyoz.catalogue.di.qualifier.ApplicationQualifier
import com.luismunyoz.catalogue.di.qualifier.UI
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Module
class ApplicationModule {


    @Provides @Singleton @ApplicationQualifier
    fun provideApplicationContext(app: Application): Context = app

    @Provides
    @UI
    fun provideUIExecutor(): Scheduler = AndroidSchedulers.mainThread()
}