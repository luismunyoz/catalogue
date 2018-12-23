package com.luismunyoz.catalogue.data.repository.catalog.datasource

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import io.reactivex.Completable
import io.reactivex.Single

interface CatalogDataSource {

    fun requestCategories(): Single<List<Category>>

    fun requestProductsForCategory(categoryId: Int): Single<List<Product>>

    fun saveCategory(category: Category): Completable

    fun saveCategoryProducts(categoryId: Int, products: List<Product>): Completable
}