package com.luismunyoz.catalogue.data.repository.catalog

import com.luismunyoz.catalogue.data.repository.catalog.datasource.CatalogDataSource
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CatalogRepositoryImplTest {

    private lateinit var repository: CatalogRepositoryImpl
    private val remote: CatalogDataSource = mock()
    private val cache: CatalogDataSource = mock()

    @BeforeEach
    internal fun setUp() {
        reset(remote, cache)
        repository = CatalogRepositoryImpl(remote, cache)
    }

    @Nested
    @DisplayName("With categories requested")
    inner class CategoriesRequested {

        @Test
        fun `should emit cache and network results`() {
            val categoriesCache = listOf(Category(2, "name"))
            val categoriesRemote = listOf(Category(1, "name"))

            ArrangeBuilder()
                    .withCacheSaveCategorySuccess()
                    .withCacheCategoriesResponse(categoriesCache)
                    .withRemoteCategoriesResponse(categoriesRemote)

            val observer = repository.getCategories().test()

            with(observer){
                assertNoErrors()
                assertValueCount(2)
                assertValues(categoriesCache, categoriesRemote)
            }

            verify(cache).saveCategories(categoriesRemote)
        }

        @Test
        fun `should emit cache result and network error`(){
            val error = Throwable()
            val categoriesCache = listOf(Category(2, "name"))

            ArrangeBuilder()
                    .withCacheCategoriesResponse(categoriesCache)
                    .withRemoteCategoriesResponse(error)

            val observer = repository.getCategories().test()

            with(observer){
                assertError(error)
                assertValueCount(1)
            }

            verify(cache, never()).saveCategories(any())
        }
    }

    @Nested
    @DisplayName("With products by category requested")
    inner class ProductsRequested {

        @Test
        fun `should emit network and cache result`() {
            val remoteProducts = listOf(Product("0", "sample", "sold_out", 0, 0, 0, ""))
            val cacheProducts = listOf(Product("0", "sample", "sold_out", 0, 0, 0, ""))

            ArrangeBuilder()
                    .withCacheSaveProductsSuccess()
                    .withCacheProductsResponse(cacheProducts)
                    .withRemoteProductsResponse(remoteProducts)

            val observer = repository.getProducts(1).test()

            with(observer){
                assertNoErrors()
                assertValueCount(2)
                assertValues(cacheProducts, remoteProducts)
            }

            verify(cache).saveCategoryProducts(1, remoteProducts)
        }

        @Test
        fun `should emit cache result and network error`(){
            val error = Throwable()
            val cacheProducts = listOf(Product("0", "sample", "sold_out", 0, 0, 0, ""))

            ArrangeBuilder()
                    .withRemoteProductsResponse(error)
                    .withCacheProductsResponse(cacheProducts)

            val observer = repository.getProducts(1).test()

            with(observer){
                assertError(error)
                assertValueCount(1)
            }

            verify(cache, never()).saveCategoryProducts(any(), any())
        }
    }

    inner class ArrangeBuilder {

        fun withRemoteCategoriesResponse(response: List<Category>): ArrangeBuilder {
            doAnswer { Single.just(response) }.whenever(remote).requestCategories()
            return this
        }

        fun withRemoteCategoriesResponse(response: Throwable): ArrangeBuilder {
            whenever(remote.requestCategories()).thenReturn(Single.error(response))
            return this
        }

        fun withCacheCategoriesResponse(response: List<Category>): ArrangeBuilder {
            doAnswer { Single.just(response) }.whenever(cache).requestCategories()
            return this
        }

        fun withRemoteProductsResponse(response: List<Product>): ArrangeBuilder {
            doAnswer { Single.just(response) }.whenever(remote).requestProductsForCategory(any())
            return this
        }

        fun withRemoteProductsResponse(response: Throwable): ArrangeBuilder {
            whenever(remote.requestProductsForCategory(any())).thenReturn(Single.error(response))
            return this
        }

        fun withCacheProductsResponse(response: List<Product>): ArrangeBuilder {
            doAnswer { Single.just(response) }.whenever(cache).requestProductsForCategory(any())
            return this
        }

        fun withCacheSaveCategorySuccess(): ArrangeBuilder {
            doAnswer { Completable.complete() }.whenever(cache).saveCategories(any())
            return this
        }

        fun withCacheSaveProductsSuccess(): ArrangeBuilder {
            doAnswer { Completable.complete() }.whenever(cache).saveCategoryProducts(any(), any())
            return this
        }
    }
}