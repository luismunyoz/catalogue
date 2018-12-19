package com.luismunyoz.catalogue.domain.interactor

import com.luismunyoz.catalogue.di.qualifier.Network
import com.luismunyoz.catalogue.domain.entity.Category
import com.luismunyoz.catalogue.domain.entity.Product
import com.luismunyoz.catalogue.domain.interactor.base.UseCase
import com.luismunyoz.catalogue.domain.interactor.base.UseCaseWithParameter
import com.luismunyoz.catalogue.domain.repository.CatalogRepository
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(val catalogRepository: CatalogRepository,
                                             @Network val scheduler: Scheduler)
    : UseCaseWithParameter<Category, List<Product>> {

    override fun execute(parameter: Category): Flowable<List<Product>> {
        return catalogRepository.getProducts(parameter)
                .subscribeOn(scheduler)
    }
}