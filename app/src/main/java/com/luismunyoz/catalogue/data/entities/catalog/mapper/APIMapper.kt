package com.luismunyoz.catalogue.data.entities.catalog.mapper

import com.luismunyoz.catalogue.data.entities.catalog.APICategory
import com.luismunyoz.catalogue.data.entities.catalog.APIProduct
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import javax.inject.Inject

class APIMapper @Inject constructor() {

    @JvmName("mapCategories")
    fun map(APICategories: List<APICategory>) : List<Category> = APICategories.map { map(it) }

    fun map(APICategory: APICategory) : Category = Category(APICategory.name, APICategory.data)

    @JvmName("mapItems")
    fun map(APIProducts: List<APIProduct>) : List<Product> = APIProducts.map { map(it) }

    fun map(APIProduct: APIProduct) : Product = Product(APIProduct.id, APIProduct.name, APIProduct.status, APIProduct.numLikes, APIProduct.numComments, APIProduct.price, APIProduct.photo)
}