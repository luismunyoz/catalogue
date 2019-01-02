package com.luismunyoz.catalogue.data.repository.catalog.datasource.cache.model

import com.google.gson.annotations.SerializedName

data class CacheProduct(
        var id: String? = null,
        var name: String? = null,
        var status: String? = null,
        var numLikes: Int = 0,
        var numComments: Int = 0,
        var price: Int = 0,
        var photo: String? = null
)