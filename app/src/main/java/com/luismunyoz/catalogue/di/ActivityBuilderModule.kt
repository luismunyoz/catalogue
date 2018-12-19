package com.luismunyoz.catalogue.di

import com.luismunyoz.catalogue.ui.screens.main.MainActivity
import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.di.CategoryProductsFragmentProvider
import com.luismunyoz.catalogue.ui.screens.main.di.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = arrayOf(
            MainActivityModule::class,
            CategoryProductsFragmentProvider::class))
    abstract fun bindMainActivity(): MainActivity
}