package com.luismunyoz.catalogue.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.luismunyoz.catalogue.data.room.dao.CatalogDao
import com.luismunyoz.catalogue.data.room.entity.RoomCategory
import com.luismunyoz.catalogue.data.room.entity.RoomProduct

@Database(entities = arrayOf(RoomCategory::class, RoomProduct::class), version = 1)
abstract class CatalogDatabase: RoomDatabase() {

    abstract fun catalogDao(): CatalogDao

}