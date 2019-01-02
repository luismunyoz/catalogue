package com.luismunyoz.catalogue.di

import android.content.Context
import com.luismunyoz.catalogue.BuildConfig
import com.luismunyoz.catalogue.data.repository.catalog.datasource.api.model.mapper.APIMapper
import com.luismunyoz.catalogue.data.repository.catalog.datasource.api.APICatalogDataSource
import com.luismunyoz.catalogue.data.repository.catalog.datasource.api.ApiService
import com.luismunyoz.catalogue.di.qualifier.Remote
import com.luismunyoz.catalogue.di.qualifier.ApplicationQualifier
import com.luismunyoz.catalogue.data.repository.catalog.datasource.CatalogDataSource
import com.luismunyoz.catalogue.data.repository.catalog.datasource.cache.model.CacheCatalogDataSource
import com.luismunyoz.catalogue.data.repository.catalog.datasource.cache.model.mapper.CacheMapper
import com.luismunyoz.catalogue.di.qualifier.Cached
import com.pacoworks.rxpaper2.RxPaperBook
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
    fun provideCache(@ApplicationQualifier context: Context) = Cache(context.cacheDir, CACHE_SIZE)

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

    @Provides @Singleton
    fun provideCategoriesCache() : RxPaperBook = RxPaperBook.with("categories")

    @Provides @Singleton @Cached
    fun providesCacheDataSource(paperBook: RxPaperBook, mapper: CacheMapper): CatalogDataSource =
            CacheCatalogDataSource(paperBook, mapper)

    companion object {
        const val CACHE_SIZE = 10 * 1024 * 1024L
    }
}