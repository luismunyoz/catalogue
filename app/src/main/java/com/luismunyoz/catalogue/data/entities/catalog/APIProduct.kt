package com.luismunyoz.catalogue.data.entities.catalog

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