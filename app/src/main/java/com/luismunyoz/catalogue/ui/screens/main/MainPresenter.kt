package com.luismunyoz.catalogue.ui.screens.main

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.interactor.GetCategoriesInteractor
import com.luismunyoz.catalogue.ui.base.BasePresenter
import com.luismunyoz.catalogue.ui.entity.mapper.UIMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(val getCategoriesInteractor: GetCategoriesInteractor,
                    val uiMapper: UIMapper) : BasePresenter<MainContract.View>(), MainContract.Presenter {

    var categories: List<Category> = listOf()

    override fun start() {
        downloadCategories()
    }

    private fun downloadCategories() {
        disposable.add(
                getCategoriesInteractor
                        .invoke()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            categories = it
                            getView()?.populateCategories(uiMapper.transform(it))
                        },{
                            getView()?.showErrorNoConnection()
                        })
        )
    }
}