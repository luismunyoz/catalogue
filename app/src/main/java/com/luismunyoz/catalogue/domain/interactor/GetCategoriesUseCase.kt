package com.luismunyoz.catalogue.domain.interactor

import com.luismunyoz.catalogue.di.qualifier.Network
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.interactor.base.UseCase
import com.luismunyoz.catalogue.domain.repository.CatalogRepository
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(val catalogRepository: CatalogRepository,
                                               @Network val scheduler: Scheduler)
    : UseCase<List<Category>> {

    override fun execute(): Flowable<List<Category>> {
        return catalogRepository.getCategories()
                .subscribeOn(scheduler)
    }
}