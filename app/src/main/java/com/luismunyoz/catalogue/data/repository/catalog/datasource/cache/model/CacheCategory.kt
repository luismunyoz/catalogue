package com.luismunyoz.catalogue.data.repository.catalog.datasource.cache.model

data class CacheCategory(
        var id: Int = -1,
        var name: String? = null,
        var products: List<CacheProduct> = listOf()
)