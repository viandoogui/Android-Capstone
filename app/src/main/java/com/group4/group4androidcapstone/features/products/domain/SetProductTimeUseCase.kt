package com.group4.group4androidcapstone.features.products.domain

import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetProductTimeUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend operator fun invoke(id: Int, viewedTime: LocalDateTime) {
        return productRepository.setProductViewedTime(id, viewedTime)
    }

}
