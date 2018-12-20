package com.luismunyoz.catalogue.ui.screens.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.ui.entity.UICategory
import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.CategoryProductsFragment

class CategoriesPagerAdapter(val fragmentManager: FragmentManager,
                             val categories: List<UICategory>)
    : FragmentStatePagerAdapter(fragmentManager) {

    val fragments: Array<Fragment?> = arrayOfNulls(categories.size)

    override fun getItem(position: Int): Fragment {
        if(fragments[position] == null){
            fragments[position] = CategoryProductsFragment.newInstance(categories[position].name)
        }
        return fragments[position]!!
    }

    override fun getCount(): Int = categories.size
}