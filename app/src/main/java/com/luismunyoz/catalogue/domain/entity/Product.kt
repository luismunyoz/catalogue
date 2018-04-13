package com.luismunyoz.catalogue.domain.entity

class Product (
    val id: String,
    val name: String,
    val status: String,
    val numLikes: Int,
    val numComments: Int,
    val price: Int,
    val photo: String
){

    fun isSoldOut() : Boolean = status.equals(STATUS_SOLD_OUT)

    companion object {
        val STATUS_ON_SALE : String = "on_sale"
        val STATUS_SOLD_OUT : String = "sold_out"
    }
}