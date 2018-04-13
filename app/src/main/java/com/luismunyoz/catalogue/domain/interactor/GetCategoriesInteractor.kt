package com.luismunyoz.catalogue.domain.interactor

import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.interactor.base.Interactor
import com.luismunyoz.catalogue.domain.repository.CatalogRepository
import io.reactivex.Single

class GetCategoriesInteractor(val catalogRepository: CatalogRepository): Interactor<List<Category>> {

    override fun invoke(): Single<List<Category>> {
        return Single.create<List<Category>> {
            val categories = catalogRepository.getCategories()
            it.onSuccess(categories)
        }
    }
}