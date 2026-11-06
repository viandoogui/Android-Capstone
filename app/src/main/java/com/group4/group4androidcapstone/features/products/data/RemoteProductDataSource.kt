package com.group4.group4androidcapstone.features.products.data

import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteProductDataSource : ProductDataSource {
    @GET("Product")
    override suspend fun getProducts(): List<ProductDto>

    @GET("Product/{productId}")
    override suspend fun getProduct(
        @Path("productId") id: Int
    ): ProductDto
}