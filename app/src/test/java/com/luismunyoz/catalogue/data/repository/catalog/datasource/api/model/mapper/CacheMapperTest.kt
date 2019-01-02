package com.luismunyoz.catalogue.data.repository.catalog.datasource.api.model.mapper

import com.luismunyoz.catalogue.data.repository.catalog.datasource.cache.model.CacheCategory
import com.luismunyoz.catalogue.data.repository.catalog.datasource.cache.model.CacheProduct
import com.luismunyoz.catalogue.data.repository.catalog.datasource.cache.model.mapper.CacheMapper
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CacheMapperTest {

    private lateinit var mapper: CacheMapper

    @Before
    fun setUp() {
        mapper = CacheMapper()
    }

    @Test
    fun `should map a CacheCategory`(){
        val toMap = CacheCategory(0, "name")

        val mapped = mapper.map(toMap)

        assertEquals(toMap.id, mapped.id)
        assertEquals(toMap.name, mapped.name)
    }

    @Test
    fun `should map a list of CacheCategories`(){
        val toMap = listOf(
                CacheCategory(0, "name"),
                CacheCategory(1, "name2")
        )

        val mapped = mapper.mapCategories(toMap)

        assertEquals(toMap[0].id, mapped[0].id)
        assertEquals(toMap[0].name, mapped[0].name)
        assertEquals(toMap[1].id, mapped[1].id)
        assertEquals(toMap[1].name, mapped[1].name)
    }

    @Test
    fun `should map a CacheProduct`(){
        val toMap = CacheProduct("0", "name", "status", 0, 0,
                0, "url")

        val mapped = mapper.map(toMap)

        assertEquals(toMap.id, mapped.id)
        assertEquals(toMap.name, mapped.name)
        assertEquals(toMap.status, mapped.status)
        assertEquals(toMap.numLikes, mapped.numLikes)
        assertEquals(toMap.numComments, mapped.numComments)
        assertEquals(toMap.price, mapped.price)
        assertEquals(toMap.photo, mapped.photo)
    }

    @Test
    fun `should map a list of CacheProduct`(){
        val toMap = listOf(
            CacheProduct("0", "name", "status", 0, 0,
                0, "url"),
            CacheProduct("1", "name", "status", 0, 0,
                    0, "url")
        )

        val mapped = mapper.mapProducts(toMap)

        assertEquals(toMap[0].id, mapped[0].id)
        assertEquals(toMap[0].name, mapped[0].name)
        assertEquals(toMap[0].status, mapped[0].status)
        assertEquals(toMap[0].numLikes, mapped[0].numLikes)
        assertEquals(toMap[0].numComments, mapped[0].numComments)
        assertEquals(toMap[0].price, mapped[0].price)
        assertEquals(toMap[0].photo, mapped[0].photo)
        assertEquals(toMap[1].id, mapped[1].id)
        assertEquals(toMap[1].name, mapped[1].name)
        assertEquals(toMap[1].status, mapped[1].status)
        assertEquals(toMap[1].numLikes, mapped[1].numLikes)
        assertEquals(toMap[1].numComments, mapped[1].numComments)
        assertEquals(toMap[1].price, mapped[1].price)
        assertEquals(toMap[1].photo, mapped[1].photo)
    }
}