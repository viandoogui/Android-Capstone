package com.group4.group4androidcapstone.features.products.domain

import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeProducts(): Flow<List<Product>>
    fun observeProductDetails(id: Int): Flow<ProductDetails?>

    suspend fun refreshProducts(): Unit
    suspend fun refreshProductDetails(id: Int): Unit
}