package com.luismunyoz.catalogue.ui.entity.mapper

import com.luismunyoz.catalogue.domain.entity.Category
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class CategoryUIMapperTest {

    private lateinit var mapper: CategoryUIMapper

    @Before
    fun setUp() {
        mapper = CategoryUIMapper()
    }

    @Test
    fun `should map a category`(){
        val category = Category(1, "data")

        val mapped = mapper.map(category)

        with(mapped){
            assertEquals(category.name, this.name)
        }
    }

    @Test
    fun `should map a list of categories`(){
        val categories = listOf(Category(1, "name1"),
                Category(2, "name2"))

        val mapped = mapper.map(categories)

        with(mapped){
            assertEquals(categories.size, this.size)
            assertEquals(categories[0].name, this[0].name)
            assertEquals(categories[1].name, this[1].name)
        }
    }
}