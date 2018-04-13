package com.luismunyoz.catalogue.di.subcomponent.main

import com.luismunyoz.catalogue.di.ActivityModule
import com.luismunyoz.catalogue.di.scope.ActivityScope
import com.luismunyoz.catalogue.domain.interactor.GetCategoriesInteractor
import com.luismunyoz.catalogue.domain.interactor.GetCategoryByNameInteractor
import com.luismunyoz.catalogue.domain.interactor.GetProductsInteractor
import com.luismunyoz.catalogue.ui.entity.mapper.UIMapper
import com.luismunyoz.catalogue.ui.screens.main.MainActivity
import com.luismunyoz.catalogue.ui.screens.main.MainContract
import com.luismunyoz.catalogue.ui.screens.main.MainPresenter
import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.CategoryProductsContract
import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.CategoryProductsPresenter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(activity: MainActivity) : ActivityModule(activity) {

    @Provides @ActivityScope
    fun provideUIMapper() = UIMapper()

    @Provides @ActivityScope
    fun provideMainPresenter(getCategoriesInteractor: GetCategoriesInteractor, uiMapper: UIMapper) : MainContract.Presenter =
            MainPresenter(getCategoriesInteractor, uiMapper)

}