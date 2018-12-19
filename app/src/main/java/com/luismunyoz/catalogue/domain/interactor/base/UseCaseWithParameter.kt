
package com.luismunyoz.catalogue.domain.interactor.base

import io.reactivex.Flowable
import io.reactivex.Single

interface UseCaseWithParameter<P, R> {

    fun execute(parameter: P): Flowable<R>
}