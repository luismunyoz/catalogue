package com.luismunyoz.catalogue.data.room

import com.luismunyoz.catalogue.data.room.entity.mapper.RoomMapper
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.repository.CatalogDataSet

class RoomCatalogDataSet(val database: CatalogDatabase): CatalogDataSet {

    override fun requestCategories(): List<Category> =
            database.catalogDao().getCategories().map { RoomMapper().transform(it) }

    override fun requestProductsForCategory(category: Category): List<Product> =
            database.catalogDao().getProductsForCategory(category.name).map { RoomMapper().transform(it) }

    override fun saveCategories(categories: List<Category>) {
        database.catalogDao().saveCategories(RoomMapper().transform(categories))
    }

    override fun saveProductsForCategory(category: Category, products: List<Product>) =
            database.catalogDao().saveProducts(RoomMapper().transform(products, category.name))

    override fun canBeUpdated(): Boolean = true
}