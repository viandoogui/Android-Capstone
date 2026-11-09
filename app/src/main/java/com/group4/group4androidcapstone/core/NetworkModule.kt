package com.group4.group4androidcapstone.core

import com.group4.group4androidcapstone.BuildConfig
import com.group4.group4androidcapstone.features.orders.data.MyOrdersDataSource
import com.group4.group4androidcapstone.features.orders.data.OrderDataSource
import com.group4.group4androidcapstone.features.orders.data.RemoteMyOrdersDataSource
import com.group4.group4androidcapstone.features.orders.data.RemoteOrderDataSource
import com.group4.group4androidcapstone.features.products.data.ProductDataSource
import com.group4.group4androidcapstone.features.products.data.RemoteProductDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AdventureWorks

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AW_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDataSource(
        retrofit: Retrofit
    ): ProductDataSource {
        return retrofit.create(
            RemoteProductDataSource::class.java
        )
    }

    @Provides
    @Singleton
    fun provideOrderDataSource(
        retrofit: Retrofit
    ): OrderDataSource {
        return retrofit.create(
            RemoteOrderDataSource::class.java
        )
    }

//    @Provides
//    @Singleton
//    fun provideMyOrderDataSource(
//        retrofit: Retrofit
//    ): MyOrdersDataSource {
//        return retrofit.create(
//            RemoteMyOrdersDataSource::class.java
//        )
//    }

}