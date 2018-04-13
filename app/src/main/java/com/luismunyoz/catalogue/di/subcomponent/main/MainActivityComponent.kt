package com.luismunyoz.catalogue.di.subcomponent.main

import com.luismunyoz.catalogue.di.scope.ActivityScope
import com.luismunyoz.catalogue.ui.screens.main.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(
        MainActivityModule::class
))
interface MainActivityComponent {

    fun injectTo(activity: MainActivity)
}