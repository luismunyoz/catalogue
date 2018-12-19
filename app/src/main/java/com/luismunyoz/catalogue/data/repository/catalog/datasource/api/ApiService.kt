package com.luismunyoz.catalogue.data.repository.catalog.datasource.api

import com.luismunyoz.catalogue.data.entities.catalog.APICategory
import com.luismunyoz.catalogue.data.entities.catalog.APIProduct
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET("/b/5c18b71533a8fe76ff4e6911/3")
    fun getCategories(): Flowable<List<APICategory>>

    @GET
    fun getItems(@Url location: String): Flowable<List<APIProduct>>
}