package com.group4.group4androidcapstone.features.products.domain

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProduct(id: Int): Product
}