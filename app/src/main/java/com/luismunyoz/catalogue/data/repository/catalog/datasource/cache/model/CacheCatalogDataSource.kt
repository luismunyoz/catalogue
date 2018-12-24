package com.luismunyoz.catalogue.data.repository.catalog.datasource.cache.model

import com.luismunyoz.catalogue.data.repository.catalog.datasource.CatalogDataSource
import com.luismunyoz.catalogue.data.repository.catalog.datasource.cache.model.mapper.CacheMapper
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CacheCatalogDataSource(val cache: RxPaperBook, val mapper: CacheMapper)
    : CatalogDataSource {

    override fun requestCategories(): Single<List<Category>> {
        return cache.read<List<CacheCategory>>("categories", listOf())
                .map { mapper.mapCategories(it) }
    }

    override fun requestProductsForCategory(categoryId: Int): Single<List<Product>> {
        return cache.read<List<CacheProduct>>("products_$categoryId", listOf())
                .map { mapper.mapProducts(it) }
    }

    override fun saveCategories(categories: List<Category>): Completable =
            cache.write("categories", mapper.mapReverseCategories(categories))

    override fun saveCategoryProducts(categoryId: Int, products: List<Product>): Completable =
            cache.write("products_$categoryId", mapper.map(products))
}