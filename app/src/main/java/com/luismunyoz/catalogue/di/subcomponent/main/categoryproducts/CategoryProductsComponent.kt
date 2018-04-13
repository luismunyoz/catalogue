package com.luismunyoz.catalogue.di.subcomponent.main.categoryproducts

import com.luismunyoz.catalogue.di.scope.ActivityScope
import com.luismunyoz.catalogue.ui.screens.main.MainActivity
import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.CategoryProductsFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(
        CategoryProductsModule::class
))
interface CategoryProductsComponent {

    fun injectTo(fragment: CategoryProductsFragment)
}