package com.luismunyoz.catalogue.domain.interactor

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.domain.interactor.base.Interactor
import com.luismunyoz.catalogue.domain.interactor.error.CategoryNotFoundError
import com.luismunyoz.catalogue.domain.repository.CatalogRepository
import io.reactivex.Single

class GetCategoryByNameInteractor(val catalogRepository: CatalogRepository): Interactor<Category> {

    var categoryName: String? = null

    override fun invoke(): Single<Category> {
        val categoryName = this.categoryName ?: throw IllegalStateException("category name cannot be null")
        return Single.create {
            val category : Category? = catalogRepository.getCategoryByName(categoryName)
            if(category != null){
                it.onSuccess(category)
            } else {
                it.onError(CategoryNotFoundError())
            }
        }
    }
}