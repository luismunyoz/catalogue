package com.luismunyoz.catalogue.data.entities.catalog.mapper

import com.luismunyoz.catalogue.data.entities.catalog.APICategory
import com.luismunyoz.catalogue.data.entities.catalog.APIProduct
import com.luismunyoz.catalogue.domain.entity.Product
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class APIMapperTest {

    private lateinit var mapper: APIMapper

    @Before
    fun setUp(){
        mapper = APIMapper()
    }

    @Test
    fun `should map a category`(){
        val category = APICategory("name", "data")

        val mapped = mapper.map(category)

        with(mapped){
            assertEquals(category.name, this.name)
            assertEquals(category.data, this.data)
        }
    }

    @Test
    fun `should map a list of categories`(){
        val categories = listOf(APICategory("name1", "data1"),
                APICategory("name2", "data2"))

        val mapped = mapper.mapCategories(categories)

        with(mapped){
            assertEquals(categories.size, this.size)
            assertEquals(categories[0].name, this[0].name)
            assertEquals(categories[0].data, this[0].data)
            assertEquals(categories[1].name, this[1].name)
            assertEquals(categories[1].data, this[1].data)
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
            assertEquals(products[0].id, this[0].id)
            assertEquals(products[0].name, this[0].name)
            assertEquals(products[0].status, this[0].status)
            assertEquals(products[0].numLikes, this[0].numLikes)
            assertEquals(products[0].numComments, this[0].numComments)
            assertEquals(products[0].price, this[0].price)
            assertEquals(products[0].photo, this[0].photo)
            assertEquals(products[1].id, this[1].id)
            assertEquals(products[1].name, this[1].name)
            assertEquals(products[1].status, this[1].status)
            assertEquals(products[1].numLikes, this[1].numLikes)
            assertEquals(products[1].numComments, this[1].numComments)
            assertEquals(products[1].price, this[1].price)
            assertEquals(products[1].photo, this[1].photo)
        }
    }
}