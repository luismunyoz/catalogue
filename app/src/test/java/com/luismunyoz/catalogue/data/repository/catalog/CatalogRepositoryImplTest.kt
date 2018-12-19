package com.luismunyoz.catalogue.data.repository.catalog

import com.luismunyoz.catalogue.data.repository.catalog.datasource.CatalogDataSource
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CatalogRepositoryImplTest {

    private lateinit var repository: CatalogRepositoryImpl
    private val remote: CatalogDataSource = mock()

    @BeforeEach
    internal fun setUp() {
        reset(remote)
        repository = CatalogRepositoryImpl(remote)
    }

    @Nested
    @DisplayName("With categories requested")
    inner class CategoriesRequested {

        @Test
        fun `should emit network result`() {
            val categories = listOf(Category("1","1"))

            ArrangeBuilder()
                    .withRemoteCategoriesResponse(categories)

            val observer = repository.getCategories().test()

            with(observer){
                assertNoErrors()
                assertValueCount(1)
                assertValueAt(0, categories)
            }
        }

        @Test
        fun `should emit network error`(){
            val error = Throwable()

            ArrangeBuilder()
                    .withRemoteCategoriesResponse(error)

            val observer = repository.getCategories().test()

            with(observer){
                assertError(error)
                assertNoValues()
            }
        }
    }

    @Nested
    @DisplayName("With category by name requested")
    inner class CategoryByNameRequested {

        @Test
        fun `should emit network result if category found`() {
            val categories = listOf(Category("1","1"))

            ArrangeBuilder()
                    .withRemoteCategoriesResponse(categories)

            val observer = repository.getCategoryByName(categories[0].name).test()

            with(observer){
                assertNoErrors()
                assertValueCount(1)
                assertValueAt(0, categories[0])
            }
        }

        @Test
        fun `should not emit if category not found`() {
            val categories = listOf(Category("1","1"))

            ArrangeBuilder()
                    .withRemoteCategoriesResponse(categories)

            val observer = repository.getCategoryByName("notFound").test()

            with(observer){
                assertNoErrors()
                assertValueCount(0)
                assertComplete()
            }
        }

        @Test
        fun `should emit network error`(){
            val error = Throwable()

            ArrangeBuilder()
                    .withRemoteCategoriesResponse(error)

            val observer = repository.getCategoryByName("1").test()

            with(observer){
                assertError(error)
                assertNoValues()
            }
        }
    }

    @Nested
    @DisplayName("With products by category requested")
    inner class ProductsRequested {

        @Test
        fun `should emit network result`() {
            val category = Category("1", "1")
            val products = listOf(Product("0", "sample", "sold_out", 0, 0, 0, ""))

            ArrangeBuilder()
                    .withRemoteProductsResponse(products)

            val observer = repository.getProducts(category).test()

            with(observer){
                assertNoErrors()
                assertValueCount(1)
                assertValueAt(0, products)
            }
        }

        @Test
        fun `should emit empty list on network error`(){
            val category = Category("1", "1")
            val error = Throwable()

            ArrangeBuilder()
                    .withRemoteProductsResponse(error)

            val observer = repository.getProducts(category).test()

            with(observer){
                assertNoErrors()
                assertValueCount(1)
                assertValue { it.isEmpty() }
            }
        }
    }

    inner class ArrangeBuilder {

        fun withRemoteCategoriesResponse(response: List<Category>): ArrangeBuilder {
            doAnswer { Flowable.just(response) }.whenever(remote).requestCategories()
            return this
        }

        fun withRemoteCategoriesResponse(response: Throwable): ArrangeBuilder {
            whenever(remote.requestCategories()).thenReturn(Flowable.error(response))
            return this
        }

        fun withRemoteProductsResponse(response: List<Product>): ArrangeBuilder {
            doAnswer { Flowable.just(response) }.whenever(remote).requestProductsForCategory(any())
            return this
        }

        fun withRemoteProductsResponse(response: Throwable): ArrangeBuilder {
            whenever(remote.requestProductsForCategory(any())).thenReturn(Flowable.error(response))
            return this
        }
    }
}