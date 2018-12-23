package com.luismunyoz.catalogue.data.repository.catalog.datasource.api

import com.luismunyoz.catalogue.data.repository.catalog.datasource.api.model.mapper.APIMapper
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.data.repository.catalog.datasource.CatalogDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class APICatalogDataSource(val apiService: ApiService,
                           val mapper: APIMapper) : CatalogDataSource {

    override fun requestCategories(): Single<List<Category>> =
        apiService.getCategories().map { mapper.mapCategories(it) }

    override fun requestProductsForCategory(categoryId: Int): Single<List<Product>> =
        apiService
                .getCategories()
                .flattenAsFlowable { it }
                .filter { it.id == categoryId }
                .firstOrError()
                .flatMap { apiService.getItems(it.data) }
                .map { mapper.mapProducts(it) }

    override fun saveCategory(category: Category): Completable {
        throw UnsupportedOperationException("Write operations not allowed")
    }

    override fun saveCategoryProducts(categoryId: Int, products: List<Product>): Completable {
        throw UnsupportedOperationException("Write operations not allowed")
    }
}