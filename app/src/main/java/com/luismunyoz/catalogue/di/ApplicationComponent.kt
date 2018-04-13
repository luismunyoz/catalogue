package com.luismunyoz.catalogue.di

import com.luismunyoz.catalogue.di.subcomponent.main.MainActivityComponent
import com.luismunyoz.catalogue.di.subcomponent.main.MainActivityModule
import com.luismunyoz.catalogue.di.subcomponent.main.categoryproducts.CategoryProductsComponent
import com.luismunyoz.catalogue.di.subcomponent.main.categoryproducts.CategoryProductsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        DataModule::class,
        RepositoryModule::class,
        DomainModule::class
))
interface ApplicationComponent {

    fun plus(module: MainActivityModule): MainActivityComponent
    fun plus(module: CategoryProductsModule) : CategoryProductsComponent
}