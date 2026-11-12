package com.group4.group4androidcapstone.features.products.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductDetailsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend operator fun invoke(id: Int): ProductDetails {
        return productRepository
            .getProductDetails(id)
    }

}