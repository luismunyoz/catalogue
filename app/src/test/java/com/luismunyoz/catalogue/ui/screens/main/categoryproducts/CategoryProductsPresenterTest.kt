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
    fun `should download category on start`(){
        val category = Category(1, "name")

        ArrangeBuilder()
                .withGetCategoryByNameSuccess(category)

        presenter.start("sample")

    }

    @Test
    fun `should download the category and then download the category products`(){
        val category = Category("name", "hello")
        val products = listOf(Product("0", "sample", "sold_out", 0, 0, 0, ""))
        val mapped = listOf(UIProduct("0", "sample", true, 0, 0, 0, ""))

        ArrangeBuilder()
                .withGetCategoryByNameSuccess(category)
                .withGetProductsSuccess(products)
                .withMapperResponse(mapped)

        presenter.start("name")

        inOrder(getCategoryByNameUseCase, getProductsUseCase, mapper, view) {
            verify(getCategoryByNameUseCase).execute("name")
            verify(view).showLoading(true)
            verify(getProductsUseCase).execute(category)
            verify(mapper).map(products)
            verify(view).populateProducts(mapped)
            verify(view).showLoading(false)
        }
    }

    @Test
    fun `should download the category and show no products if there are no products`(){
        val category = Category("name", "hello")

        ArrangeBuilder()
                .withGetCategoryByNameSuccess(category)
                .withGetProductsSuccess(listOf())

        presenter.start("name")

        inOrder(getCategoryByNameUseCase, getProductsUseCase, mapper, view) {
            verify(getCategoryByNameUseCase).execute("name")
            verify(view).showLoading(true)
            verify(getProductsUseCase).execute(category)
            verify(view).showNoProducts()
            verify(view).showLoading(false)
        }
    }

    @Test
    fun `should download the category and show errors if products download fails`(){
        val category = Category("name", "hello")

        ArrangeBuilder()
                .withGetCategoryByNameSuccess(category)
                .withGetProductsError(Throwable())

        presenter.start("name")

        inOrder(getCategoryByNameUseCase, getProductsUseCase, mapper, view) {
            verify(getCategoryByNameUseCase).execute("name")
            verify(view).showLoading(true)
            verify(getProductsUseCase).execute(category)
            verify(view).showErrorNoConnection()
            verify(view).showLoading(false)
        }
    }

    @Test
    fun `should show error if category download fails`(){

        ArrangeBuilder()
                .withGetCategoryByNameError(Throwable())

        presenter.start("name")

        inOrder(getCategoryByNameUseCase, getProductsUseCase, mapper, view) {
            verify(getCategoryByNameUseCase).execute("name")
            verify(view).showLoading(true)
            verify(view).showLoading(false)
            verify(view).showErrorNoConnection()
        }
    }


    inner class ArrangeBuilder {

        fun withGetCategoryByNameSuccess(category: Category) : ArrangeBuilder {
            doAnswer { Flowable.just(category) }.whenever(getCategoryByNameUseCase).execute(any())
            return this
        }

        fun withGetCategoryByNameError(error: Throwable): ArrangeBuilder {
            whenever(getCategoryByNameUseCase.execute(any()))
                    .thenReturn(Flowable.error(error))
            return this
        }

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