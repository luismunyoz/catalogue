package com.luismunyoz.catalogue.ui.entity.mapper

import com.luismunyoz.catalogue.domain.entity.Product
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class ProductUIMapperTest {

    private lateinit var mapper: ProductUIMapper

    @Before
    fun setUp() {
        mapper = ProductUIMapper()
    }

    @Test
    fun `should map a product`(){
        val product = Product("0", "sample", "sold_out", 0, 0, 0, "")

        val mapped = mapper.map(product)

        with(mapped){
            assertEquals(product.id, this.id)
            assertEquals(product.name, this.name)
            assertEquals(true, this.isSoldOut)
            assertEquals(product.numLikes, this.numLikes)
            assertEquals(product.numComments, this.numComments)
            assertEquals(product.price, this.price)
            assertEquals(product.photo, this.photo)
        }
    }

    @Test
    fun `should map a list of products`(){
        val products = listOf(Product("0", "sample", "sold_out", 0, 0, 0, ""),
                Product("1", "sample2", "not_sold_out", 0, 0, 0, ""))

        val mapped = mapper.map(products)

        with(mapped){
            assertEquals(products[0].id, this[0].id)
            assertEquals(products[0].name, this[0].name)
            assertEquals(true, this[0].isSoldOut)
            assertEquals(products[0].numLikes, this[0].numLikes)
            assertEquals(products[0].numComments, this[0].numComments)
            assertEquals(products[0].price, this[0].price)
            assertEquals(products[0].photo, this[0].photo)
            assertEquals(products[1].id, this[1].id)
            assertEquals(products[1].name, this[1].name)
            assertEquals(false, this[1].isSoldOut)
            assertEquals(products[1].numLikes, this[1].numLikes)
            assertEquals(products[1].numComments, this[1].numComments)
            assertEquals(products[1].price, this[1].price)
            assertEquals(products[1].photo, this[1].photo)
        }
    }
}