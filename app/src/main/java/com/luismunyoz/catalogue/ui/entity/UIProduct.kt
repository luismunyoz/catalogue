package com.luismunyoz.catalogue.ui.entity

data class UIProduct (
    val id: String,
    val name: String,
    val isSoldOut: Boolean,
    val numLikes: Int,
    val numComments: Int,
    val price: Int,
    val photo: String
){
    fun getLikes(): String = numLikes.toString()

    fun getComments(): String = numComments.toString()

    fun getReadablePrice(): String = "$ $price"
}