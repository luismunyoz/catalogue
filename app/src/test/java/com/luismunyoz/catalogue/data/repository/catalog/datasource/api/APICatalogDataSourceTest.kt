package com.luismunyoz.catalogue.data.repository.catalog.datasource.api

import com.luismunyoz.catalogue.data.repository.catalog.datasource.api.model.APICategory
import com.luismunyoz.catalogue.data.repository.catalog.datasource.api.model.APIProduct
import com.luismunyoz.catalogue.data.repository.catalog.datasource.api.model.mapper.APIMapper
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.jupiter.api.*

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
            val categories = listOf(APICategory(1, "1", "data"))
            val mapped = listOf(Category(1, "2"))

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
            val categories = listOf(APICategory(1, "2", "data"))
            val products = listOf(APIProduct("0", "sample", "sold_out", 0, 0, 0, ""))
            val mapped = listOf(Product("0", "sample", "sold_out", 0, 0, 0, ""))

            ArrangeBuilder()
                    .withServiceCategoriesResponse(categories)
                    .withServiceProductsResponse(products)
                    .withMapperProductsResponse(mapped)

            val observer = datasource.requestProductsForCategory(1).test()

            with(observer) {
                assertNoErrors()
                assertValueCount(1)
                assertValueAt(0, mapped)
            }
        }

        @Test
        fun `should emit error if category not found`() {
            val categories = listOf(APICategory(1, "2", "data"))
            val products = listOf(APIProduct("0", "sample", "sold_out", 0, 0, 0, ""))
            val mapped = listOf(Product("0", "sample", "sold_out", 0, 0, 0, ""))

            ArrangeBuilder()
                    .withServiceCategoriesResponse(categories)
                    .withServiceProductsResponse(products)
                    .withMapperProductsResponse(mapped)

            val observer = datasource.requestProductsForCategory(0).test()

            with(observer) {
                assertError(NoSuchElementException::class.java)
                assertNoValues()
            }
        }

        @Test
        fun `should emit network error`() {
            val categories = listOf(APICategory(1, "2", "data"))
            val error = Throwable()

            ArrangeBuilder()
                    .withServiceCategoriesResponse(categories)
                    .withServiceProductsResponse(error)

            val observer = datasource.requestProductsForCategory(1).test()

            with(observer) {
                assertError(error)
                assertNoValues()
            }
        }
    }

    @Nested
    @DisplayName("With categories save requested")
    inner class CategoriesSaveRequested {

        @Test
        fun `should emit error`() {
            val categories = listOf(Category(1, "2"))

            val observer = datasource.saveCategories(categories).test()

            with(observer) {
                assertError(UnsupportedOperationException::class.java)
                assertNoValues()
            }
        }
    }

    @Nested
    @DisplayName("With products save requested")
    inner class ProductsSaveRequested {

        @Test
        fun `should emit error`() {
            val products = listOf(Product("0", "sample", "sold_out", 0, 0, 0, ""))

            val observer = datasource.saveCategoryProducts(0, products).test()

            with(observer) {
                //assertThrows(UnsupportedOperationException(), {})
                assertNoValues()
            }
        }
    }


    inner class ArrangeBuilder {

        fun withServiceCategoriesResponse(response: List<APICategory>): ArrangeBuilder {
            doAnswer { Single.just(response) }.whenever(apiService).getCategories()
            return this
        }

        fun withServiceCategoriesResponse(response: Throwable): ArrangeBuilder {
            whenever(apiService.getCategories()).thenReturn(Single.error(response))
            return this
        }

        fun withServiceProductsResponse(response: List<APIProduct>): ArrangeBuilder {
            doAnswer { Single.just(response) }.whenever(apiService).getItems(any())
            return this
        }

        fun withServiceProductsResponse(response: Throwable): ArrangeBuilder {
            whenever(apiService.getItems(any())).thenReturn(Single.error(response))
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