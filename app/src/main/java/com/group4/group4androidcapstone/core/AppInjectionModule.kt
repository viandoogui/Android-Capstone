package com.group4.group4androidcapstone.core

import com.group4.group4androidcapstone.features.orders.data.DefaultOrderRepository
import com.group4.group4androidcapstone.features.orders.data.MyOrdersRepository
import com.group4.group4androidcapstone.features.orders.domain.OrderRepository
import com.group4.group4androidcapstone.features.products.data.RemoteProductRepository
import com.group4.group4androidcapstone.features.products.domain.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
abstract class AppInjectionModule {

    @Binds
    abstract fun bindOrderRepository(
        implementation: DefaultOrderRepository
    ): OrderRepository

    @Binds
    abstract fun bindProductRepository(
        implementation: RemoteProductRepository
    ): ProductRepository

    companion object {
        @Provides
        @Singleton
        @ApplicationScope
        fun provideAppScope(): CoroutineScope {
            return CoroutineScope(SupervisorJob() + Dispatchers.Default)
        }
    }
}