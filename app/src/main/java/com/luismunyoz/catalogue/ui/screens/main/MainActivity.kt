package com.luismunyoz.catalogue.ui.screens.main

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.luismunyoz.catalogue.R
import com.luismunyoz.catalogue.ui.base.BaseActivity
import com.luismunyoz.catalogue.ui.entity.UICategory
import com.luismunyoz.catalogue.ui.screens.main.adapter.CategoriesPagerAdapter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity: BaseActivity<MainContract.View, MainContract.Presenter>(),
        MainContract.View, HasSupportFragmentInjector {

    @Inject
    override lateinit var presenter : MainContract.Presenter

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(mainToolbar)

        presenter.start()
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
        mainPager.addOnPageChangeListener( object: ViewPager.OnPageChangeListener {
            override fun onPageSelected(p0: Int) {
                mainTabs.getTabAt(p0)?.select()
            }

            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
        })
    }

    override fun showErrorNoConnection() {
        Toast.makeText(this, getString(R.string.error_downloading_categories), Toast.LENGTH_LONG).show()
    }
}