package com.luismunyoz.catalogue.data.repository.catalog.datasource.api

import com.luismunyoz.catalogue.data.entities.catalog.APICategory
import com.luismunyoz.catalogue.data.entities.catalog.APIProduct
import com.luismunyoz.catalogue.data.entities.catalog.mapper.APIMapper
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.ui.entity.UIProduct
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class APICatalogDataSourceTest {


    private lateinit var datasource: APICatalogDataSource

    private val apiService: ApiService = mock()
    private val mapper: APIMapper = mock()

    @BeforeEach
    internal fun setUp() {
        reset(apiService, mapper)
        datasource = APICatalogDataSource(apiService, mapper)
    }

    @Nested
    @DisplayName("With categories requested")
    inner class CategoriesRequested {

        @Test
        fun `should emit network result`() {
            val categories = listOf(APICategory("1", "1"))
            val mapped = listOf(Category("1", "1"))

            ArrangeBuilder()
                    .withServiceCategoriesResponse(categories)
                    .withMapperCategoriesResponse(mapped)

            val observer = datasource.requestCategories().test()

            with(observer) {
                assertNoErrors()
                assertValueCount(1)
                assertValueAt(0, mapped)
            }
        }

        @Test
        fun `should emit network error`() {
            val error = Throwable()

            ArrangeBuilder()
                    .withServiceCategoriesResponse(error)

            val observer = datasource.requestCategories().test()

            with(observer) {
                assertError(error)
                assertNoValues()
            }
        }
    }

    @Nested
    @DisplayName("With products requested")
    inner class ProductsRequested {

        @Test
        fun `should emit network result`() {
            val products = listOf(APIProduct("0", "sample", "sold_out", 0, 0, 0, ""))
            val mapped = listOf(Product("0", "sample", "sold_out", 0, 0, 0, ""))

            ArrangeBuilder()
                    .withServiceProductsResponse(products)
                    .withMapperProductsResponse(mapped)

            val observer = datasource.requestProductsForCategory(Category("0", "0")).test()

            with(observer) {
                assertNoErrors()
                assertValueCount(1)
                assertValueAt(0, mapped)
            }
        }

        @Test
        fun `should emit network error`() {
            val error = Throwable()

            ArrangeBuilder()
                    .withServiceProductsResponse(error)

            val observer = datasource.requestProductsForCategory(Category("0", "0")).test()

            with(observer) {
                assertError(error)
                assertNoValues()
            }
        }
    }

    inner class ArrangeBuilder {

        fun withServiceCategoriesResponse(response: List<APICategory>): ArrangeBuilder {
            doAnswer { Flowable.just(response) }.whenever(apiService).getCategories()
            return this
        }

        fun withServiceCategoriesResponse(response: Throwable): ArrangeBuilder {
            whenever(apiService.getCategories()).thenReturn(Flowable.error(response))
            return this
        }

        fun withServiceProductsResponse(response: List<APIProduct>): ArrangeBuilder {
            doAnswer { Flowable.just(response) }.whenever(apiService).getItems(any())
            return this
        }

        fun withServiceProductsResponse(response: Throwable): ArrangeBuilder {
            whenever(apiService.getItems(any())).thenReturn(Flowable.error(response))
            return this
        }

        fun withMapperCategoriesResponse(response: List<Category>): ArrangeBuilder {
            doAnswer { response }.whenever(mapper).mapCategories(any())
            return this
        }

        fun withMapperProductsResponse(response: List<Product>): ArrangeBuilder {
            doAnswer { response }.whenever(mapper).mapProducts(any())
            return this
        }
    }
}