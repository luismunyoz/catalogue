package com.luismunyoz.catalogue.di

import com.luismunyoz.catalogue.di.qualifier.ApiQualifier
import com.luismunyoz.catalogue.di.qualifier.RoomQualifier
import com.luismunyoz.catalogue.domain.repository.CatalogRepository
import com.luismunyoz.catalogue.repository.CatalogDataSet
import com.luismunyoz.catalogue.repository.CatalogRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides @Singleton
    fun provideCatalogRepository(@ApiQualifier apiCatalogDataSet: CatalogDataSet, @RoomQualifier roomCatalogDataSet: CatalogDataSet): CatalogRepository = CatalogRepositoryImpl(listOf(apiCatalogDataSet))
}