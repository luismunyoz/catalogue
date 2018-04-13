package com.luismunyoz.catalogue.ui.screens.main

import com.luismunyoz.catalogue.ui.base.BaseContract
import com.luismunyoz.catalogue.ui.entity.UICategory

interface MainContract {

    interface View : BaseContract.View {

        fun populateCategories(categories: List<UICategory>)

        fun showErrorNoConnection()
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun start()
    }
}