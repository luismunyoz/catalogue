package com.luismunyoz.catalogue.ui.screens.main

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.interactor.GetCategoriesUseCase
import com.luismunyoz.catalogue.ui.base.BasePresenter
import com.luismunyoz.catalogue.ui.entity.mapper.UIMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(val getCategoriesInteractor: GetCategoriesUseCase,
                                        val uiMapper: UIMapper) : BasePresenter<MainContract.View>(),
        MainContract.Presenter {

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
                            getView()?.populateCategories(uiMapper.map(it))
                        }, {
                            getView()?.showErrorNoConnection()
                        })
        )
    }
}