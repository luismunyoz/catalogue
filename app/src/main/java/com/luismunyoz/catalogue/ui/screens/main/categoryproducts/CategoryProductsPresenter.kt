package com.luismunyoz.catalogue.ui.screens.main.categoryproducts

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.domain.interactor.GetCategoryByNameInteractor
import com.luismunyoz.catalogue.domain.interactor.GetProductsInteractor
import com.luismunyoz.catalogue.domain.interactor.error.CategoryNotFoundError
import com.luismunyoz.catalogue.ui.base.BasePresenter
import com.luismunyoz.catalogue.ui.entity.mapper.UIMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CategoryProductsPresenter(val getCategoryByNameInteractor: GetCategoryByNameInteractor,
                                val getProductsInteractor: GetProductsInteractor,
                                val uiMapper: UIMapper) : BasePresenter<CategoryProductsContract.View>(), CategoryProductsContract.Presenter {

    var category: Category? = null
    var products: List<Product> = listOf()

    override fun start(categoryName : String?) {
        getView()?.showLoading(true)
        getCategoryByNameInteractor.categoryName = categoryName
        disposable.add(
                getCategoryByNameInteractor
                        .invoke()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            downloadProducts(it)
                        }, {
                            getView()?.showLoading(false)
                            if(it is CategoryNotFoundError){
                                getView()?.showErrorNoCategoryFound()
                            } else {
                                getView()?.showErrorNoConnection()
                            }
                        })
        )
    }

    private fun downloadProducts(category: Category){
        this.category = category
        getProductsInteractor.category = category
        disposable.add(
                getProductsInteractor
                        .invoke()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            products = it
                            if (products.isNotEmpty()) {
                                getView()?.populateProducts(uiMapper.transform(products))
                            } else{
                                getView()?.showNoProducts()
                            }
                            getView()?.showLoading(false)
                        }, {
                            getView()?.showErrorNoConnection()
                            getView()?.showLoading(false)
                        })
        )
    }

}