package com.group4.group4androidcapstone.features.products.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockProductDataSource @Inject constructor() : ProductDataSource {
    private val products = listOf(
        ProductDto(
            productId = 1,
            name = "Product 1",
            productNumber = "21432152",
            summary = "Summary of Product 1",
            thumbnailPhoto = "https://example.com/product1.jpg",
            thumbnailPhotoFileName = "product1.jpg",
            warranty = "1 year",
            color = "Red",
            listPrice = 9.99
        ),
        ProductDto(
            productId = 2,
            name = "Product 2",
            productNumber = "32123145",
            summary = "Summary of Product 2",
            thumbnailPhoto = "https://example.com/product2.jpg",
            thumbnailPhotoFileName = "product2.jpg",
            warranty = null,
            color = "Blue",
            listPrice = 4.99
        ),
        ProductDto(
            productId = 3,
            name = "Product 3",
            productNumber = "56465138543",
            summary = "Summary of Product 3",
            thumbnailPhoto = "https://example.com/product3.jpg",
            thumbnailPhotoFileName = "product3.jpg",
            warranty = null,
            color = "Green",
            listPrice = 19.99
        ),
        ProductDto(
            productId = 1,
            name = "Product 4",
            productNumber = "123413526423",
            summary = "Summary of Product 4",
            thumbnailPhoto = "https://example.com/product4.jpg",
            thumbnailPhotoFileName = "product4.jpg",
            warranty = "1 year",
            color = "Neon",
            listPrice = 9.99
        ),
    )

    override suspend fun getProducts(): List<ProductDto> {
        return products
    }

    override suspend fun getProduct(id: Int): ProductDto {
        return products.first { it.productId == id }
    }
}