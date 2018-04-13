package com.luismunyoz.catalogue.data.api

import com.luismunyoz.catalogue.data.api.entity.APICategory
import com.luismunyoz.catalogue.data.api.entity.APIProduct
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET("/m-et/Android/json/master.json")
    fun getCategories(): Call<List<APICategory>>

    @GET
    fun getItems(@Url location: String): Call<List<APIProduct>>
}