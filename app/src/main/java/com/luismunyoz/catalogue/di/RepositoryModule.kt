package com.luismunyoz.catalogue.di

import com.luismunyoz.catalogue.di.qualifier.Remote
import com.luismunyoz.catalogue.domain.repository.CatalogRepository
import com.luismunyoz.catalogue.data.repository.catalog.datasource.CatalogDataSource
import com.luismunyoz.catalogue.data.repository.catalog.CatalogRepositoryImpl
import com.luismunyoz.catalogue.di.qualifier.Network
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Network
    fun provideNetworkScheduler(): Scheduler = Schedulers.io()

    @Provides @Singleton
    fun provideCatalogRepository(@Remote apiCatalogDataSource: CatalogDataSource): CatalogRepository
            = CatalogRepositoryImpl(apiCatalogDataSource)
}