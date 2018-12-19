package com.luismunyoz.catalogue.ui.screens.main

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.interactor.GetCategoriesUseCase
import com.luismunyoz.catalogue.ui.entity.UICategory
import com.luismunyoz.catalogue.ui.entity.mapper.CategoryUIMapper
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MainPresenterTest {

    var view: MainContract.View = mock()
    var getCategoriesUseCase: GetCategoriesUseCase = mock()
    var mapper : CategoryUIMapper = mock()
    private val scheduler: Scheduler = Schedulers.trampoline()

    private lateinit var presenter: MainPresenter

    @Before
    fun setUp(){
        reset(view, getCategoriesUseCase, mapper)
        presenter = MainPresenter(getCategoriesUseCase, mapper, scheduler).also { it.attachView(view) }
    }

    @Test
    fun `should download categories on start`(){
        ArrangeBuilder()
                .withGetCategoriesResponse(buildCategories())

        presenter.start()

        verify(getCategoriesUseCase).execute()
    }

    @Test
    fun `should populate categories when downloaded`(){
        val categories = buildCategories()
        val mapped = listOf(UICategory("1"), UICategory("2"))

        ArrangeBuilder()
                .withGetCategoriesResponse(categories)
                .withMapperResponse(mapped)

        presenter.start()

        inOrder(getCategoriesUseCase, view, mapper){
            verify(getCategoriesUseCase).execute()
            verify(mapper).map(categories)
            verify(view).populateCategories(mapped)
        }
    }

    @Test
    fun `should show error when error downloading categories`(){

        ArrangeBuilder()
                .withGetCategoriesError(Throwable())

        presenter.start()

        inOrder(getCategoriesUseCase, view){
            verify(getCategoriesUseCase).execute()
            verify(view).showErrorNoConnection()
        }
    }

    private fun buildCategories(): List<Category> {
        return listOf(Category("1", "http://www.first.com"),
                Category("2", "http://www.second.com"))
    }

    inner class ArrangeBuilder {

        fun withGetCategoriesResponse(list: List<Category>) : ArrangeBuilder {
            doAnswer { Flowable.just(list) }.whenever(getCategoriesUseCase).execute()
            return this
        }

        fun withGetCategoriesError(error: Throwable): ArrangeBuilder {
            whenever(getCategoriesUseCase.execute())
                    .thenReturn(Flowable.error(error))
            return this
        }

        fun withMapperResponse(uiCategories: List<UICategory>): ArrangeBuilder {
            doAnswer { uiCategories }.whenever(mapper).map(Mockito.anyList())
            return this
        }
    }
}