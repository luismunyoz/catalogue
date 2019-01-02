package com.luismunyoz.catalogue.ui.screens.main

import com.luismunyoz.catalogue.di.qualifier.UI
import com.luismunyoz.catalogue.domain.interactor.GetCategoriesUseCase
import com.luismunyoz.catalogue.ui.base.BasePresenter
import com.luismunyoz.catalogue.ui.entity.mapper.CategoryUIMapper
import io.reactivex.Scheduler
import javax.inject.Inject

class MainPresenter @Inject constructor(private val getCategoriesInteractor: GetCategoriesUseCase,
                                        val mapper: CategoryUIMapper,
                                        @UI val scheduler: Scheduler) : BasePresenter<MainContract.View>(),
        MainContract.Presenter {

    override fun start() {
        downloadCategories()
    }

    private fun downloadCategories() {
        disposable.add(
                getCategoriesInteractor
                        .execute()
                        .observeOn(scheduler)
                        .subscribe({
                            getView()?.populateCategories(mapper.map(it))
                        }, {
                            getView()?.showErrorNoConnection()
                        })
        )
    }
}