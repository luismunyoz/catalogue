package com.luismunyoz.catalogue.data.room.entity.mapper

import com.luismunyoz.catalogue.data.api.entity.APICategory
import com.luismunyoz.catalogue.data.api.entity.APIProduct
import com.luismunyoz.catalogue.data.room.entity.RoomCategory
import com.luismunyoz.catalogue.data.room.entity.RoomProduct
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product

class RoomMapper {

    @JvmName("transformRoomCategories")
    fun transform(roomCategories: List<RoomCategory>) : List<Category> = roomCategories.map { transform(it) }

    fun transform(roomCategory: RoomCategory) : Category = Category(roomCategory.name, roomCategory.data)

    @JvmName("transformRoomItems")
    fun transform(roomProducts: List<RoomProduct>) : List<Product> = roomProducts.map { transform(it) }

    fun transform(roomProduct: RoomProduct) : Product = Product(roomProduct.id, roomProduct.name, roomProduct.status, roomProduct.numLikes, roomProduct.numComments, roomProduct.price, roomProduct.photo)

    @JvmName("transformCategories")
    fun transform(categories: List<Category>) : List<RoomCategory> = categories.map { transform(it) }

    fun transform(category: Category) : RoomCategory = RoomCategory(category.name, category.data)

    @JvmName("transformItems")
    fun transform(products: List<Product>, categoryName: String) : List<RoomProduct> = products.map { transform(it, categoryName) }

    fun transform(product: Product, categoryName: String) : RoomProduct = RoomProduct(product.id, product.name, product.status, product.numLikes, product.numComments, product.price, product.photo, categoryName)
}