package com.luismunyoz.catalogue.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.luismunyoz.catalogue.data.room.entity.RoomCategory
import com.luismunyoz.catalogue.data.room.entity.RoomProduct
import io.reactivex.Flowable

@Dao
interface CatalogDao {

    @Query("SELECT * from categories")
    fun getCategories(): List<RoomCategory>

    @Query("SELECT * from products where category_name LIKE :categoryName")
    fun getProductsForCategory(categoryName: String) : List<RoomProduct>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCategories(categories: List<RoomCategory>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveProducts(products: List<RoomProduct>)
}