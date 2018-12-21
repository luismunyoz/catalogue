package com.luismunyoz.catalogue.data.repository.catalog.datasource.api

import com.luismunyoz.catalogue.data.entities.catalog.mapper.APIMapper
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.data.repository.catalog.datasource.CatalogDataSource
import io.reactivex.Flowable

class APICatalogDataSource(val apiService: ApiService,
                           val mapper: APIMapper) : CatalogDataSource {

    override fun requestCategories(): Flowable<List<Category>> =
        apiService.getCategories().map { mapper.mapCategories(it) }

    override fun requestProductsForCategory(category: Category): Flowable<List<Product>> =
        apiService.getItems(category.data).map { mapper.mapProducts(it) }

}