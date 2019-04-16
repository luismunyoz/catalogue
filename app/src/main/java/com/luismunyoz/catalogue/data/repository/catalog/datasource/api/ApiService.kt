package com.luismunyoz.catalogue.data.repository.catalog.datasource.api

import com.luismunyoz.catalogue.data.repository.catalog.datasource.api.model.APICategory
import com.luismunyoz.catalogue.data.repository.catalog.datasource.api.model.APIProduct
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface ApiService {

    @Headers("secret-key: \$2a\$10\$PhkCjpeB5/6MBYbuN1UpHuJUrAPLRKMXQEaayHfKEMRJ7s/kfCw8W")
    @GET("/b/5c18b71533a8fe76ff4e6911/4")
    fun getCategories(): Single<List<APICategory>>

    @Headers("secret-key: \$2a\$10\$PhkCjpeB5/6MBYbuN1UpHuJUrAPLRKMXQEaayHfKEMRJ7s/kfCw8W")
    @GET
    fun getItems(@Url location: String): Single<List<APIProduct>>
}