package com.luismunyoz.catalogue.ui.screens.main

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.widget.Toast
import com.luismunyoz.catalogue.R
import com.luismunyoz.catalogue.di.ApplicationComponent
import com.luismunyoz.catalogue.di.subcomponent.main.MainActivityModule
import com.luismunyoz.catalogue.ui.base.BaseActivity
import com.luismunyoz.catalogue.ui.entity.UICategory
import com.luismunyoz.catalogue.ui.screens.main.adapter.CategoriesPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity: BaseActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    @Inject
    override lateinit var presenter : MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(mainToolbar)

        presenter.start()
    }

    override fun injectDependencies(applicationComponent: ApplicationComponent) {
        applicationComponent.plus(MainActivityModule(this)).injectTo(this)
    }

    override fun populateCategories(categories: List<UICategory>) {
        populateTabs(categories)
        populatePager(categories)
    }

    private fun populateTabs(categories: List<UICategory>) {
        mainTabs.removeAllTabs()
        categories.forEach {
            mainTabs.addTab(mainTabs.newTab().setText(it.name))
        }
        mainTabs.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    mainPager.currentItem = it.position
                }
            }
        })
    }

    private fun populatePager(categories: List<UICategory>) {
        val adapter = CategoriesPagerAdapter(supportFragmentManager, categories)
        mainPager.adapter = adapter
    }

    override fun showErrorNoConnection() {
        Toast.makeText(this, getString(R.string.error_downloading_categories), Toast.LENGTH_LONG).show()
    }
}