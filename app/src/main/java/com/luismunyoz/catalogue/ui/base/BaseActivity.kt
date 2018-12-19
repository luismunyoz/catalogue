package com.luismunyoz.catalogue.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.luismunyoz.catalogue.App
import com.luismunyoz.catalogue.di.ApplicationComponent

abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>> : AppCompatActivity(), BaseContract.View {

    open lateinit var presenter: P

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}