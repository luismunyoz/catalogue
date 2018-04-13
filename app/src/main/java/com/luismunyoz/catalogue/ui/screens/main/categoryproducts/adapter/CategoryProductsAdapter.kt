package com.luismunyoz.catalogue.ui.screens.main.categoryproducts.adapter

import com.luismunyoz.catalogue.R
import com.luismunyoz.catalogue.ui.base.BaseAdapter
import com.luismunyoz.catalogue.ui.entity.UIProduct

class CategoryProductsAdapter(var items: List<UIProduct>): BaseAdapter() {
    override fun getItemForPosition(position: Int): Any {
        return items[position]
    }

    override fun getListener(): Any = Any()

    override fun getLayoutIdForPosition(position: Int): Int  = R.layout.layout_product_item

    override fun getItemCount(): Int = items.size

}