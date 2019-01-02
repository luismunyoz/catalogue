package com.luismunyoz.catalogue.domain.repository

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import io.reactivex.Flowable

interface CatalogRepository {

    fun getCategories(): Flowable<List<Category>>

    fun getProducts(categoryId: Int): Flowable<List<Product>>
}