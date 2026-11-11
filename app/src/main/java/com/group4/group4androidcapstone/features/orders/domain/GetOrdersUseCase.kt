package com.group4.group4androidcapstone.features.orders.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetOrdersUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {

    suspend operator fun invoke(): List<Order> {
        return orderRepository
            .getOrders()
            .sortedByDescending { it.orderDate }
    }

}