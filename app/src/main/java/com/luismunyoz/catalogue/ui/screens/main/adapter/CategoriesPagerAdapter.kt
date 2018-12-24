package com.luismunyoz.catalogue.ui.screens.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.luismunyoz.catalogue.ui.entity.UICategory
import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.CategoryProductsFragment

class CategoriesPagerAdapter(val fragmentManager: FragmentManager,
                             val categories: List<UICategory>)
    : FragmentStatePagerAdapter(fragmentManager) {

    val fragments: Array<Fragment?> = arrayOfNulls(categories.size)

    override fun getItem(position: Int): Fragment {
        if(fragments[position] == null){
            fragments[position] = CategoryProductsFragment.newInstance(categories[position].id)
        }
        return fragments[position]!!
    }

    override fun getCount(): Int = categories.size
}