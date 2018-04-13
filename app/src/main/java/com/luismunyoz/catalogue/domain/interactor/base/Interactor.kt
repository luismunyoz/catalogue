
package com.luismunyoz.catalogue.domain.interactor.base

import io.reactivex.Single

interface Interactor<T> {

    operator fun invoke(): Single<T>
}