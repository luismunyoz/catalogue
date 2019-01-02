package com.luismunyoz.catalogue.data.repository.catalog.datasource.api.model

import com.google.gson.annotations.SerializedName

class APIProduct (
    val id: String,
    val name: String,
    val status: String,
    @SerializedName("num_likes") val numLikes: Int,
    @SerializedName("num_comments") val numComments: Int,
    val price: Int,
    val photo: String
)