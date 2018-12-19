package com.luismunyoz.catalogue.ui.screens.main.categoryproducts

import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.presenter.CategoryProductsPresenter
import dagger.Module
import dagger.Provides

@Module
class CategoryProductsModule {

    @Provides
    fun providesCategoryProductsPresenter(presenter: CategoryProductsPresenter): CategoryProductsContract.Presenter =
            presenter
}