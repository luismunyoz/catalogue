package com.luismunyoz.catalogue.ui.screens.main.categoryproducts.di

import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.CategoryProductsFragment
import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.CategoryProductsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CategoryProductsFragmentProvider {

    @ContributesAndroidInjector(modules = [CategoryProductsModule::class])
    abstract fun provideCategoryProductsFragmentFactory(): CategoryProductsFragment

}