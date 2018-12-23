package com.luismunyoz.catalogue.data.repository.catalog.datasource.cache.model

import com.luismunyoz.catalogue.data.repository.catalog.datasource.CatalogDataSource
import com.luismunyoz.catalogue.data.repository.catalog.datasource.cache.model.mapper.CacheMapper
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Completable
import io.reactivex.Single

class CacheCatalogDataSource(val cache: RxPaperBook, val mapper: CacheMapper)
    : CatalogDataSource {

    override fun requestCategories(): Single<List<Category>> {
        return cache.read<List<CacheCategory>>("categories")
                .map { mapper.mapCategories(it) }
    }

    override fun requestProductsForCategory(categoryId: Int): Single<List<Product>> {
        return cache.read<List<CacheCategory>>("categories")
                .flattenAsFlowable { it }
                .filter { it.id == categoryId }
                .map { it.products }
                .firstOrError()
                .map { mapper.mapProducts(it) }
    }

    override fun saveCategory(category: Category): Completable {
        //TODO
        return Completable.complete()
    }

    override fun saveCategoryProducts(categoryId: Int, products: List<Product>): Completable {
        //TODO
        return Completable.complete()
    }
}