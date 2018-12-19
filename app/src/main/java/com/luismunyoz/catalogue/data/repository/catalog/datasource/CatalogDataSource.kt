package com.luismunyoz.catalogue.data.repository.catalog.datasource

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import io.reactivex.Flowable

interface CatalogDataSource {

    fun requestCategories(): Flowable<List<Category>>

    fun requestProductsForCategory(category: Category): Flowable<List<Product>>
}