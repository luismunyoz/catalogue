package com.luismunyoz.catalogue.data.entities.catalog.mapper

import com.luismunyoz.catalogue.data.entities.catalog.APICategory
import com.luismunyoz.catalogue.data.entities.catalog.APIProduct
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import javax.inject.Inject

class APIMapper @Inject constructor() {

    fun mapCategories(categories: List<APICategory>) : List<Category> = categories.map { map(it) }

    fun map(category: APICategory) : Category = Category(category.name, category.data)

    fun mapProducts(products: List<APIProduct>) : List<Product> = products.map { map(it) }

    fun map(product: APIProduct) : Product = Product(product.id, product.name, product.status,
            product.numLikes, product.numComments, product.price, product.photo)
}