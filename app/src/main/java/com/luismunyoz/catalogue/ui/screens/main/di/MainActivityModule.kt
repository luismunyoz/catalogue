package com.luismunyoz.catalogue.ui.screens.main.di

import android.support.v4.app.FragmentActivity
import com.luismunyoz.catalogue.ui.screens.main.MainActivity
import com.luismunyoz.catalogue.ui.screens.main.MainContract
import com.luismunyoz.catalogue.ui.screens.main.MainPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun provideActivity(activity: MainActivity): FragmentActivity

    @Binds
    abstract fun provideMainPresenter(presenter: MainPresenter)
            : MainContract.Presenter
}