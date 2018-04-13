package com.luismunyoz.catalogue.di.subcomponent.main.categoryproducts

import com.luismunyoz.catalogue.di.scope.ActivityScope
import com.luismunyoz.catalogue.domain.interactor.GetCategoryByNameInteractor
import com.luismunyoz.catalogue.domain.interactor.GetProductsInteractor
import com.luismunyoz.catalogue.ui.entity.mapper.UIMapper
import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.CategoryProductsContract
import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.CategoryProductsPresenter
import dagger.Module
import dagger.Provides

@Module
class CategoryProductsModule {

    @Provides @ActivityScope
    fun provideUIMapper() = UIMapper()

    @Provides @ActivityScope
    fun provideCategoryProductsPresenter(getCategoryByNameInteractor: GetCategoryByNameInteractor, getProductsInteractor: GetProductsInteractor, uiMapper: UIMapper) : CategoryProductsContract.Presenter =
            CategoryProductsPresenter(getCategoryByNameInteractor, getProductsInteractor, uiMapper)
}