package com.luismunyoz.catalogue.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luismunyoz.catalogue.App
import com.luismunyoz.catalogue.di.ApplicationComponent

abstract class BaseFragment<V : BaseContract.View, P : BaseContract.Presenter<V>>: Fragment(), BaseContract.View {

    open lateinit var presenter: P

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View? = inflater.inflate(getLayoutId(), container, false)
        presenter.attachView(this as V)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    abstract fun getLayoutId(): Int
}