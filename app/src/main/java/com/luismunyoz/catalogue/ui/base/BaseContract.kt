package com.luismunyoz.catalogue.ui.base

import io.reactivex.disposables.CompositeDisposable

interface BaseContract {

    interface Presenter<V : View> {

        fun attachView(view: V)

        fun detachView()
    }

    interface View
}