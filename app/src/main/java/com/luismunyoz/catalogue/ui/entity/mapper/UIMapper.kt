package com.luismunyoz.catalogue.ui.entity.mapper

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.ui.entity.UICategory
import com.luismunyoz.catalogue.ui.entity.UIProduct

class UIMapper {

    @JvmName("transformCategories")
    fun transform(categories: List<Category>) : List<UICategory> = categories.map { transform(it) }

    fun transform(category: Category) : UICategory = UICategory(category.name)

    @JvmName("transformItems")
    fun transform(products: List<Product>) : List<UIProduct> = products.map { transform(it) }

    fun transform(product: Product) : UIProduct = UIProduct(product.id, product.name, product.isSoldOut(), product.numLikes, product.numComments, product.price, product.photo)

}