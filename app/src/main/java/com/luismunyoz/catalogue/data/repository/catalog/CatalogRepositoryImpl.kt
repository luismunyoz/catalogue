package com.luismunyoz.catalogue.data.repository.catalog

import com.luismunyoz.catalogue.data.repository.catalog.datasource.CatalogDataSource
import com.luismunyoz.catalogue.di.qualifier.Remote
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.domain.repository.CatalogRepository
import io.reactivex.Flowable

class CatalogRepositoryImpl(@Remote val remoteDatasource: CatalogDataSource) : CatalogRepository {

    override fun getCategories(): Flowable<List<Category>> {
        return remoteDatasource
                .requestCategories()
    }

    override fun getCategoryByName(name: String): Flowable<Category> {
        return remoteDatasource
                .requestCategories()
                .flatMapIterable { it }
                .filter { it.name == name }
    }

    override fun getProducts(category: Category): Flowable<List<Product>> {
        return remoteDatasource
                .requestProductsForCategory(category)
                .onErrorReturn { emptyList() }
    }

}