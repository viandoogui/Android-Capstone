package com.group4.group4androidcapstone.features.products.data

interface ProductDataSource {
    suspend fun getProducts(): List<ProductDto>
    suspend fun getProduct(id: Int): ProductDetailsDto
}