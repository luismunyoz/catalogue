
package com.luismunyoz.catalogue.domain.interactor.base

import io.reactivex.Flowable
import io.reactivex.Single

interface UseCase<T> {

    operator fun invoke(): Flowable<T>
}