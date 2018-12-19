package com.luismunyoz.catalogue.domain.interactor

import com.luismunyoz.catalogue.di.qualifier.Network
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.interactor.base.UseCase
import com.luismunyoz.catalogue.domain.interactor.base.UseCaseWithParameter
import com.luismunyoz.catalogue.domain.interactor.error.CategoryNotFoundError
import com.luismunyoz.catalogue.domain.repository.CatalogRepository
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class GetCategoryByNameUseCase @Inject constructor(val catalogRepository: CatalogRepository,
                                                   @Network val scheduler: Scheduler)
    : UseCaseWithParameter<String, Category> {

    override fun execute(parameter: String): Flowable<Category> {
        return catalogRepository.getCategoryByName(parameter)
                .subscribeOn(scheduler)
    }
}