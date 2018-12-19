package com.luismunyoz.catalogue.ui.entity.mapper

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.ui.entity.UICategory
import com.luismunyoz.catalogue.ui.entity.UIProduct
import javax.inject.Inject

class CategoryUIMapper @Inject constructor() {

    fun map(categories: List<Category>) : List<UICategory> = categories.map { map(it) }

    fun map(category: Category) : UICategory = UICategory(category.name)

}