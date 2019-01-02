package com.luismunyoz.catalogue.ui.screens.main.categoryproducts

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.domain.interactor.GetProductsUseCase
import com.luismunyoz.catalogue.ui.entity.UIProduct
import com.luismunyoz.catalogue.ui.entity.mapper.ProductUIMapper
import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.presenter.CategoryProductsPresenter
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CategoryProductsPresenterTest {

    var view: CategoryProductsContract.View = mock()
    var getProductsUseCase: GetProductsUseCase = mock()
    var mapper : ProductUIMapper = mock()
    private val scheduler: Scheduler = Schedulers.trampoline()

    lateinit var presenter: CategoryProductsPresenter

    @Before
    fun setUp(){
        reset(view, getProductsUseCase, mapper)
        presenter = CategoryProductsPresenter(getProductsUseCase, mapper, scheduler).also {
            it.attachView(view)
        }
    }

    @Test
    fun `should download the category products`(){
        val products = listOf(Product("0", "sample", "sold_out", 0, 0, 0, ""))
        val mapped = listOf(UIProduct("0", "sample", true, 0, 0, 0, ""))

        ArrangeBuilder()
                .withGetProductsSuccess(products)
                .withMapperResponse(mapped)

        presenter.start(0)

        inOrder(getProductsUseCase, mapper, view) {
            verify(getProductsUseCase).execute(0)
            verify(view).showLoading(true)
            verify(mapper).map(products)
            verify(view).populateProducts(mapped)
            verify(view).showLoading(false)
        }
    }

    @Test
    fun `should show no products if there are no products`(){
        ArrangeBuilder()
                .withGetProductsSuccess(listOf())

        presenter.start(0)

        inOrder(getProductsUseCase, mapper, view) {
            verify(getProductsUseCase).execute(0)
            verify(view).showLoading(true)
            verify(view).showNoProducts()
            verify(view).showLoading(false)
        }
    }

    @Test
    fun `should show error if products download fails`(){
        ArrangeBuilder()
                .withGetProductsError(Throwable())

        presenter.start(0)

        inOrder(getProductsUseCase, mapper, view) {
            verify(getProductsUseCase).execute(0)
            verify(view).showLoading(true)
            verify(view).showErrorNoConnection()
            verify(view).showLoading(false)
        }
    }

    inner class ArrangeBuilder {

        fun withGetProductsSuccess(products: List<Product>) : ArrangeBuilder {
            doAnswer { Flowable.just(products) }.whenever(getProductsUseCase).execute(any())
            return this
        }

        fun withGetProductsError(error: Throwable): ArrangeBuilder {
            whenever(getProductsUseCase.execute(any()))
                    .thenReturn(Flowable.error(error))
            return this
        }

        fun withMapperResponse(uiProducts: List<UIProduct>): ArrangeBuilder {
            doAnswer { uiProducts }.whenever(mapper).map(Mockito.anyList())
            return this
        }
    }
}