package com.luismunyoz.catalogue.ui.screens.main.categoryproducts

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.luismunyoz.catalogue.R
import com.luismunyoz.catalogue.di.ApplicationComponent
import com.luismunyoz.catalogue.ui.base.BaseFragment
import com.luismunyoz.catalogue.ui.entity.UIProduct
import com.luismunyoz.catalogue.ui.screens.main.categoryproducts.adapter.CategoryProductsAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_categoryproducts.*
import javax.inject.Inject

class CategoryProductsFragment: BaseFragment<CategoryProductsContract.View, CategoryProductsContract.Presenter>(), CategoryProductsContract.View {

    @Inject
    override lateinit var presenter: CategoryProductsContract.Presenter

    override fun getLayoutId(): Int = R.layout.fragment_categoryproducts

    var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments?.getString(ARG_NAME)
        AndroidSupportInjection.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start(name)
    }

    override fun showErrorNoConnection() {
        Toast.makeText(context, getString(R.string.no_connection), Toast.LENGTH_LONG).show()
    }

    override fun showErrorNoCategoryFound() {
        Toast.makeText(context, getString(R.string.category_not_found), Toast.LENGTH_LONG).show()
    }

    override fun showNoProducts() {
        categoryproductsList.visibility = View.GONE
        categoryproductsLoader.visibility = View.GONE
        categoryproductsNoProducts.visibility = View.VISIBLE
    }

    override fun showLoading(loading: Boolean) {
        categoryproductsList.visibility = if(loading) View.GONE else View.VISIBLE
        categoryproductsLoader.visibility = if(loading) View.VISIBLE else View.GONE
        categoryproductsNoProducts.visibility = View.GONE
    }

    override fun populateProducts(products: List<UIProduct>) {
        val adapter = CategoryProductsAdapter(products)
        categoryproductsList.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        categoryproductsList.adapter = adapter
    }

    companion object {
        private val ARG_NAME = "NAME"

        fun newInstance(categoryName : String) : CategoryProductsFragment {
            val args: Bundle = Bundle()
            args.putString(ARG_NAME, categoryName)
            val fragment = CategoryProductsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}