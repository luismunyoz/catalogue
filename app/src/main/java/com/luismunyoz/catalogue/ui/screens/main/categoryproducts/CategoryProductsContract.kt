package com.luismunyoz.catalogue.ui.screens.main.categoryproducts

import com.luismunyoz.catalogue.ui.base.BaseContract
import com.luismunyoz.catalogue.ui.entity.UICategory
import com.luismunyoz.catalogue.ui.entity.UIProduct

interface CategoryProductsContract {

    interface View : BaseContract.View {

        fun showErrorNoConnection()

        fun showErrorNoCategoryFound()

        fun showNoProducts()

        fun showLoading(loading: Boolean)

        fun populateProducts(products: List<UIProduct>)
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun start(categoryName: String?)
    }
}