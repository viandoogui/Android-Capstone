package com.group4.group4androidcapstone.features.products.domain

import javax.inject.Inject

class RefreshProductDetailsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.refreshProductDetails(id)
    }
}