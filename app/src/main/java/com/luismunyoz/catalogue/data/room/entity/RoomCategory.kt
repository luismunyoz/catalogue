package com.luismunyoz.catalogue.data.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "categories")
data class RoomCategory(
        @PrimaryKey @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "data") var data: String
    )