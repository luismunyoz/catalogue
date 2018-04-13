package com.luismunyoz.catalogue.repository

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product

interface CatalogDataSet {

    fun requestCategories(): List<Category>

    fun requestProductsForCategory(category: Category): List<Product>
}