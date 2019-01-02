package com.luismunyoz.catalogue.data.repository.catalog

import com.luismunyoz.catalogue.data.repository.catalog.datasource.CatalogDataSource
import com.luismunyoz.catalogue.di.qualifier.Cached
import com.luismunyoz.catalogue.di.qualifier.Remote
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.domain.repository.CatalogRepository
import io.reactivex.Flowable
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(@Remote val remoteDatasource: CatalogDataSource,
                            @Cached val cacheDataSource: CatalogDataSource) : CatalogRepository {

    override fun getCategories(): Flowable<List<Category>> =
            Flowable.merge(
                    cacheDataSource.requestCategories().toFlowable(),
                    requestRemoteAndUpdateCache())

    override fun getProducts(categoryId: Int): Flowable<List<Product>> =
        Flowable.merge(
                cacheDataSource.requestProductsForCategory(categoryId).toFlowable(),
                requestProductsRemoteAndUpdateCache(categoryId))

    private fun requestRemoteAndUpdateCache(): Flowable<List<Category>> {
        return remoteDatasource.requestCategories()
                .toFlowable()
                .flatMap { categories ->
                    cacheDataSource.saveCategories(categories)
                            .andThen(Flowable.just(categories))
                }
    }

    private fun requestProductsRemoteAndUpdateCache(categoryId: Int): Flowable<List<Product>> {
        return remoteDatasource
                .requestProductsForCategory(categoryId)
                .toFlowable()
                .flatMap { products ->
                    cacheDataSource.saveCategoryProducts(categoryId, products)
                            .andThen(Flowable.just(products))
                }
    }
}