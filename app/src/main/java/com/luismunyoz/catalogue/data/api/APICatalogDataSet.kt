package com.luismunyoz.catalogue.data.api

import com.luismunyoz.catalogue.data.api.entity.mapper.APIMapper
import com.luismunyoz.catalogue.data.unwrapCall
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.repository.CatalogDataSet

class APICatalogDataSet(val apiService: ApiService) : CatalogDataSet {
    override fun requestCategories(): List<Category> =
        apiService.getCategories().unwrapCall { APIMapper().transform(this) } ?: emptyList()


    override fun requestProductsForCategory(category: Category): List<Product> =
        apiService.getItems(category.data).unwrapCall { APIMapper().transform(this) } ?: emptyList()

}