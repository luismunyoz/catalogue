package com.luismunyoz.catalogue.ui.base

import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference


abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

    var view: WeakReference<V>? = null

    val disposable: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: V) {
        this.view = WeakReference(view)
    }

    override fun detachView() {
        view?.clear()
        view = null
        disposable.clear()
    }

    protected fun getView() = view?.get()
}