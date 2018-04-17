package com.luismunyoz.catalogue.di

import android.arch.persistence.room.Room
import android.content.Context
import com.luismunyoz.catalogue.BuildConfig
import com.luismunyoz.catalogue.data.api.APICatalogDataSet
import com.luismunyoz.catalogue.data.api.ApiService
import com.luismunyoz.catalogue.data.room.CatalogDatabase
import com.luismunyoz.catalogue.data.room.RoomCatalogDataSet
import com.luismunyoz.catalogue.di.qualifier.ApiQualifier
import com.luismunyoz.catalogue.di.qualifier.ApplicationQualifier
import com.luismunyoz.catalogue.di.qualifier.RoomQualifier
import com.luismunyoz.catalogue.repository.CatalogDataSet
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
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
                .baseUrl("https://s3-ap-northeast-1.amazonaws.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides @Singleton
    fun providesAPIService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides @Singleton @ApiQualifier
    fun providesApiDataSource(apiService: ApiService): CatalogDataSet = APICatalogDataSet(apiService)

    @Provides @Singleton
    fun providesCatalogDatabase(@ApplicationQualifier context: Context) : CatalogDatabase = Room.databaseBuilder(context, CatalogDatabase::class.java, "catalog").build()

    @Provides @Singleton @RoomQualifier
    fun providesRoomDataSource(catalogDatabase: CatalogDatabase) : RoomCatalogDataSet = RoomCatalogDataSet(catalogDatabase)
}