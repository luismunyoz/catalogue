package com.luismunyoz.catalogue.domain.interactor

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.domain.interactor.base.Interactor
import com.luismunyoz.catalogue.domain.repository.CatalogRepository
import io.reactivex.Single

class GetProductsInteractor(val catalogRepository: CatalogRepository): Interactor<List<Product>> {

    var category: Category? = null

    override fun invoke(): Single<List<Product>> {
        val category = this.category ?: throw IllegalStateException("category cannot be null")
        return Single.create {
            it.onSuccess(catalogRepository.getProducts(category))
        }
    }
}