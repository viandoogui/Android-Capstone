package com.group4.group4androidcapstone.features.products.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend operator fun invoke(): List<Product> {
        return productRepository
            .getProducts()
            .sortedByDescending { it.name }
    }

}