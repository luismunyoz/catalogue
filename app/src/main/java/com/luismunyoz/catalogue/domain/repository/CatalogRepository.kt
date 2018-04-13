package com.luismunyoz.catalogue.domain.repository

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product

interface CatalogRepository {

    fun getCategories(): List<Category>

    fun getCategoryByName(name: String) : Category?

    fun getProducts(category: Category): List<Product>
}