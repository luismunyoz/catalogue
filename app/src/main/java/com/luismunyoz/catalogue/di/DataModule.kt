package com.luismunyoz.catalogue.di

import android.content.Context
import com.luismunyoz.catalogue.BuildConfig
import com.luismunyoz.catalogue.data.entities.catalog.mapper.APIMapper
import com.luismunyoz.catalogue.data.repository.catalog.datasource.api.APICatalogDataSource
import com.luismunyoz.catalogue.data.repository.catalog.datasource.api.ApiService
import com.luismunyoz.catalogue.di.qualifier.Remote
import com.luismunyoz.catalogue.di.qualifier.ApplicationQualifier
import com.luismunyoz.catalogue.data.repository.catalog.datasource.CatalogDataSource
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides @Singleton
    fun provideCache(@ApplicationQualifier context: Context) = Cache(context.cacheDir, 10 * 1024 * 1024.toLong())

    @Provides @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient =
            OkHttpClient().newBuilder()
                    .cache(cache)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
                    })
                    .build()

    @Provides @Singleton
    fun provideRestAdapter(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.jsonbin.io")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides @Singleton
    fun providesAPIService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides @Singleton @Remote
    fun providesApiDataSource(apiService: ApiService, mapper: APIMapper): CatalogDataSource =
            APICatalogDataSource(apiService, mapper)
}