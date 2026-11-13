package com.group4.group4androidcapstone.features.products.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

     operator fun invoke(): Flow<List<Product>> {
        return productRepository
            .observeProducts().map { list ->
                list.sortedByDescending {
                    it.name
                }
            }
    }

}