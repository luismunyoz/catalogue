package com.luismunyoz.catalogue.data.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "products")
data class RoomProduct(
        @PrimaryKey
        @ColumnInfo(name = "id") var id: String,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "status") var status: String,
        @ColumnInfo(name = "num_likes") var numLikes: Int,
        @ColumnInfo(name = "num_comments") var numComments: Int,
        @ColumnInfo(name = "price") var price: Int,
        @ColumnInfo(name = "photo") var photo: String,
        @ColumnInfo(name = "category_name") var categoryName: String
    )