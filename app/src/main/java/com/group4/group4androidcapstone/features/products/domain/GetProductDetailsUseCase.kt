package com.group4.group4androidcapstone.features.products.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductDetailsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

     operator fun invoke(id: Int): Flow<ProductDetails?> {
        return productRepository
            .observeProductDetails(id)
    }

}