package com.luismunyoz.catalogue.repository

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.domain.repository.CatalogRepository

class CatalogRepositoryImpl(val catalogDatasets: List<CatalogDataSet>) : CatalogRepository {

    override fun getCategories(): List<Category> {
        return catalogDatasets
                .map {
                    it.requestCategories()
                }
                .firstOrNull() ?: emptyList()
    }

    override fun getCategoryByName(name: String): Category? {
        return catalogDatasets
                .map {
                    it.requestCategories()
                }
                .filter {
                    it.isNotEmpty()
                }
                .map {
                    it.filter {
                        it.name == name
                    }.firstOrNull()
                }
                .firstOrNull()
    }

    override fun getProducts(category: Category): List<Product> {
        return catalogDatasets
                .map { it.requestProductsForCategory(category) }
                .firstOrNull() ?: emptyList()
    }

}