package com.luismunyoz.catalogue.ui.screens.main.di

import android.support.v4.app.FragmentActivity
import com.luismunyoz.catalogue.di.scope.ActivityScope
import com.luismunyoz.catalogue.domain.interactor.GetCategoriesUseCase
import com.luismunyoz.catalogue.ui.entity.mapper.UIMapper
import com.luismunyoz.catalogue.ui.screens.main.MainActivity
import com.luismunyoz.catalogue.ui.screens.main.MainContract
import com.luismunyoz.catalogue.ui.screens.main.MainPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun provideActivity(activity: MainActivity): FragmentActivity

    @Binds
    abstract fun provideMainPresenter(presenter: MainPresenter)
            : MainContract.Presenter
}