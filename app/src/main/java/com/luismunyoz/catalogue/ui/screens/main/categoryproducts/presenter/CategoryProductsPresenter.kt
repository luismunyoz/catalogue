package com.luismunyoz.catalogue.ui.screens.main.categoryproducts.presenter

import com.luismunyoz.catalogue.di.qualifier.UI
import com.luismunyoz.catalogue.domain.interactor.GetProductsUseCase
import com.luismunyoz.catalogue.ui.base.BasePresenter
import com.luismunyoz.catalogue.ui.entity.mapper.ProductUIMapper
import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.CategoryProductsContract
import io.reactivex.Scheduler
import javax.inject.Inject

class CategoryProductsPresenter @Inject constructor(private val getProductsUseCase: GetProductsUseCase,
                                                    private val mapper: ProductUIMapper,
                                                    @UI private val scheduler: Scheduler)
    : BasePresenter<CategoryProductsContract.View>(), CategoryProductsContract.Presenter {

    override fun start(categoryId: Int?) {
        categoryId?.let {
            downloadProducts(it)
        } ?: getView()?.showErrorNoCategoryFound()
    }

    private fun downloadProducts(categoryId: Int) {
        disposable.add(
                getProductsUseCase
                        .execute(categoryId)
                        .doOnSubscribe { getView()?.showLoading(true) }
                        .observeOn(scheduler)
                        .doOnNext { getView()?.showLoading(false) }
                        .subscribe({ products ->
                            if (products.isNotEmpty()) {
                                getView()?.populateProducts(mapper.map(products))
                            } else {
                                getView()?.showNoProducts()
                            }
                        }, {
                            getView()?.showErrorNoConnection()
                        })
        )
    }

}