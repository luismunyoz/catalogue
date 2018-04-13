package com.luismunyoz.catalogue.ui.screens.main

import com.luismunyoz.catalogue.RxSchedulersOverrideRule
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.interactor.GetCategoriesInteractor
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

class MainPresenterTest {

    @Rule
    @JvmField
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock
    var view: MainContract.View = mock()
    @Mock
    var getCategoriesInteractor: GetCategoriesInteractor = mock()

    var uiMapper = UIMapper()
    var categories = listOf(Category("1", ""), Category("2", ""))

    private val presenter = MainPresenter(getCategoriesInteractor, uiMapper)

    @Before
    fun setUp(){
        reset(view, getCategoriesInteractor)
        presenter.attachView(view)

        whenever(getCategoriesInteractor.invoke()).thenReturn(
                Single.just(categories)
        )
    }

    @Test
    fun `should download categories on start`(){
        presenter.start()

        verify(getCategoriesInteractor).invoke()
    }

    @Test
    fun `should populate categories when downloaded`(){
        presenter.start()

        verify(view).populateCategories(uiMapper.transform(categories))
    }

    @Test
    fun `should show error when error downloading categories`(){

        whenever(getCategoriesInteractor.invoke()).thenReturn(
                Single.error(Throwable())
        )

        presenter.start()

        verify(view).showErrorNoConnection()
    }
}