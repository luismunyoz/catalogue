package com.luismunyoz.catalogue.ui.screens.main.categoryproducts

import com.luismunyoz.catalogue.RxSchedulersOverrideRule
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.domain.interactor.GetCategoryByNameInteractor
import com.luismunyoz.catalogue.domain.interactor.GetProductsInteractor
import com.luismunyoz.catalogue.domain.interactor.error.CategoryNotFoundError
import com.luismunyoz.catalogue.ui.entity.mapper.UIMapper
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class CategoryProductsPresenterTest {

    @Rule
    @JvmField
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock
    var view: CategoryProductsContract.View = mock()
    @Mock
    var getProductsInteractor: GetProductsInteractor = mock()
    @Mock
    var getCategoryByNameInteractor: GetCategoryByNameInteractor = mock()

    var uiMapper = UIMapper()
    var category = Category("sample", "http://url")
    var products = listOf(Product("0", "sample", "sold_out", 0, 0, 0, ""))

    private val presenter = CategoryProductsPresenter(getCategoryByNameInteractor, getProductsInteractor, uiMapper)

    @Before
    fun setUp(){
        reset(view, getProductsInteractor, getCategoryByNameInteractor)
        presenter.attachView(view)

        whenever(getCategoryByNameInteractor.invoke()).thenReturn(
                Single.just(category)
        )
        whenever(getProductsInteractor.invoke()).thenReturn(
                Single.just(products)
        )
    }

    @Test
    fun `should download category on start`(){
        presenter.start("sample")

        verify(getCategoryByNameInteractor).invoke()
    }

    @Test
    fun `should show loader when loading category`(){
        presenter.start("sample")

        verify(view).showLoading(true)
    }

    @Test
    fun `should download category products when category downloaded`(){
        presenter.start("sample")

        verify(getProductsInteractor).invoke()
    }

    @Test
    fun `should show error if category not found`(){
        whenever(getCategoryByNameInteractor.invoke()).thenReturn(
                Single.error(CategoryNotFoundError())
        )
        presenter.start("not found")

        verify(view).showErrorNoCategoryFound()
    }

    @Test
    fun `should show error if category no connection`(){
        whenever(getCategoryByNameInteractor.invoke()).thenReturn(
                Single.error(Throwable())
        )
        presenter.start("error")

        verify(view).showErrorNoConnection()
    }

    @Test
    fun `should hide loader when error loading category`(){
        whenever(getCategoryByNameInteractor.invoke()).thenReturn(
                Single.error(Throwable())
        )
        presenter.start("error")

        verify(view).showLoading(false)
    }

    @Test
    fun `should populate products when loaded`(){
        presenter.start("sample")

        verify(view).populateProducts(uiMapper.transform(products))
    }

    @Test
    fun `should hide loader when products loaded`(){
        presenter.start("sample")

        verify(view).showLoading(false)
    }

    @Test
    fun `should hide loader if products load fails`(){
        whenever(getProductsInteractor.invoke()).thenReturn(
                Single.error(Throwable())
        )

        presenter.start("error")

        verify(view).showLoading(false)
    }

    @Test
    fun `should show error if products load fails`(){
        whenever(getProductsInteractor.invoke()).thenReturn(
                Single.error(Throwable())
        )

        presenter.start("error")

        verify(view).showErrorNoConnection()
    }

    @Test
    fun `should show empty view if products list is empty`(){
        whenever(getProductsInteractor.invoke()).thenReturn(
                Single.just(listOf())
        )

        presenter.start("empty")

        verify(view).showNoProducts()
    }

}