package com.luismunyoz.catalogue.data.repository.catalog.datasource.cache.model.mapper
import com.luismunyoz.catalogue.data.repository.catalog.datasource.cache.model.CacheCategory
import com.luismunyoz.catalogue.data.repository.catalog.datasource.cache.model.CacheProduct
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import javax.inject.Inject

class CacheMapper @Inject constructor() {

    fun mapCategories(categories: List<CacheCategory>) : List<Category> = categories.map { map(it) }

    fun map(category: CacheCategory) : Category = Category(category.id, category.name ?: "")

    fun mapProducts(products: List<CacheProduct>) : List<Product> = products.map { map(it) }

    fun map(product: CacheProduct) : Product = Product(product.id ?: "",
            product.name ?: "",
            product.status ?: "",
            product.numLikes, product.numComments, product.price,
            product.photo ?: "")

    fun map(category: Category) : CacheCategory = CacheCategory(category.id, category.name)

    fun map(product: Product) : CacheProduct = CacheProduct(
            product.id, product.name, product.status, product. numLikes, product.numComments,
            product.price, product.photo
    )

    fun map(products: List<Product>) = products.map { map(it) }

    fun mapReverseCategories(categories: List<Category>) = categories.map { map(it) }
}