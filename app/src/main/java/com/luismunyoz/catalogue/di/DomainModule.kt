package com.luismunyoz.catalogue.di

import com.luismunyoz.catalogue.domain.interactor.GetCategoriesInteractor
import com.luismunyoz.catalogue.domain.interactor.GetCategoryByNameInteractor
import com.luismunyoz.catalogue.domain.interactor.GetProductsInteractor
import com.luismunyoz.catalogue.domain.repository.CatalogRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetCategoriesInteractor(catalogRepository: CatalogRepository) : GetCategoriesInteractor = GetCategoriesInteractor(catalogRepository)

    @Provides
    fun provideGetProductsInteractor(catalogRepository: CatalogRepository) : GetProductsInteractor = GetProductsInteractor(catalogRepository)

    @Provides
    fun provideGetCategoryByNameInteractor(catalogRepository: CatalogRepository) : GetCategoryByNameInteractor = GetCategoryByNameInteractor(catalogRepository)
}