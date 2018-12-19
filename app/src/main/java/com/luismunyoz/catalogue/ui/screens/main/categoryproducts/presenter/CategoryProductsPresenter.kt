package com.luismunyoz.catalogue.ui.screens.main.categoryproducts.presenter

import com.luismunyoz.catalogue.di.qualifier.UI
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.interactor.GetCategoryByNameUseCase
import com.luismunyoz.catalogue.domain.interactor.GetProductsUseCase
import com.luismunyoz.catalogue.domain.interactor.error.CategoryNotFoundError
import com.luismunyoz.catalogue.ui.base.BasePresenter
import com.luismunyoz.catalogue.ui.entity.mapper.UIMapper
import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.CategoryProductsContract
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CategoryProductsPresenter @Inject constructor(val getCategoryByNameUseCase: GetCategoryByNameUseCase,
                                                    val getProductsUseCase: GetProductsUseCase,
                                                    val uiMapper: UIMapper,
                                                    @UI val scheduler: Scheduler)
    : BasePresenter<CategoryProductsContract.View>(), CategoryProductsContract.Presenter {

    override fun start(categoryName: String?) {
        categoryName?.let {
            disposable.add(
                    getCategoryByNameUseCase
                            .execute(it)
                            .doOnSubscribe { getView()?.showLoading(true) }
                            .observeOn(scheduler)
                            .subscribe({ category ->
                                downloadProducts(category)
                            }, {
                                getView()?.showLoading(false)
                                if (it is CategoryNotFoundError) {
                                    getView()?.showErrorNoCategoryFound()
                                } else {
                                    getView()?.showErrorNoConnection()
                                }
                            })
            )
        }
    }

    private fun downloadProducts(category: Category) {
        disposable.add(
                getProductsUseCase
                        .execute(category)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(scheduler)
                        .doFinally { getView()?.showLoading(false) }
                        .subscribe({ products ->
                            if (products.isNotEmpty()) {
                                getView()?.populateProducts(uiMapper.map(products))
                            } else {
                                getView()?.showNoProducts()
                            }
                        }, {
                            getView()?.showErrorNoConnection()
                        })
        )
    }

}