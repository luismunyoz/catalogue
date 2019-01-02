package com.luismunyoz.catalogue.data.repository.catalog.datasource.api.model.mapper

import com.luismunyoz.catalogue.data.repository.catalog.datasource.api.model.APICategory
import com.luismunyoz.catalogue.data.repository.catalog.datasource.api.model.APIProduct
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class APIMapperTest {

    private lateinit var mapper: APIMapper

    @Before
    fun setUp(){
        mapper = APIMapper()
    }

    @Test
    fun `should map a category`(){
        val category = APICategory(1,"name", "data")

        val mapped = mapper.map(category)

        with(mapped){
            assertEquals(category.name, this.name)
        }
    }

    @Test
    fun `should map a list of categories`(){
        val categories = listOf(APICategory(1, "name1", "data1"),
                APICategory(1, "name2", "data2"))

        val mapped = mapper.mapCategories(categories)

        with(mapped){
            Assert.assertEquals(categories.size, this.size)
            Assert.assertEquals(categories[0].id, this[0].id)
            Assert.assertEquals(categories[0].name, this[0].name)
            Assert.assertEquals(categories[1].id, this[1].id)
            Assert.assertEquals(categories[1].name, this[1].name)
        }
    }

    @Test
    fun `should map a product`(){
        val product = APIProduct("0", "sample", "sold_out", 0, 0, 0, "")

        val mapped = mapper.map(product)

        with(mapped){
            assertEquals(product.id, this.id)
            assertEquals(product.name, this.name)
            assertEquals(product.status, this.status)
            assertEquals(product.numLikes, this.numLikes)
            assertEquals(product.numComments, this.numComments)
            assertEquals(product.price, this.price)
            assertEquals(product.photo, this.photo)
        }
    }

    @Test
    fun `should map a list of products`(){
        val products = listOf(APIProduct("0", "sample", "sold_out", 0, 0, 0, ""),
                APIProduct("1", "sample2", "sold_out", 0, 0, 0, ""))

        val mapped = mapper.mapProducts(products)

        with(mapped){
            Assert.assertEquals(products[0].id, this[0].id)
            Assert.assertEquals(products[0].name, this[0].name)
            Assert.assertEquals(products[0].status, this[0].status)
            Assert.assertEquals(products[0].numLikes, this[0].numLikes)
            Assert.assertEquals(products[0].numComments, this[0].numComments)
            Assert.assertEquals(products[0].price, this[0].price)
            Assert.assertEquals(products[0].photo, this[0].photo)
            Assert.assertEquals(products[1].id, this[1].id)
            Assert.assertEquals(products[1].name, this[1].name)
            Assert.assertEquals(products[1].status, this[1].status)
            Assert.assertEquals(products[1].numLikes, this[1].numLikes)
            Assert.assertEquals(products[1].numComments, this[1].numComments)
            Assert.assertEquals(products[1].price, this[1].price)
            Assert.assertEquals(products[1].photo, this[1].photo)
        }
    }
}
