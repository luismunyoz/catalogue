package com.luismunyoz.catalogue.data.api.entity.mapper

import com.luismunyoz.catalogue.data.api.entity.APICategory
import com.luismunyoz.catalogue.data.api.entity.APIProduct
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product

class APIMapper {

    @JvmName("transformCategories")
    fun transform(APICategories: List<APICategory>) : List<Category> = APICategories.map { transform(it) }

    fun transform(APICategory: APICategory) : Category = Category(APICategory.name, APICategory.data)

    @JvmName("transformItems")
    fun transform(APIProducts: List<APIProduct>) : List<Product> = APIProducts.map { transform(it) }

    fun transform(APIProduct: APIProduct) : Product = Product(APIProduct.id, APIProduct.name, APIProduct.status, APIProduct.numLikes, APIProduct.numComments, APIProduct.price, APIProduct.photo)
}