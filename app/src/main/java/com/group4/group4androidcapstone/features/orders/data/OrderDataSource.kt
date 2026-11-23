package com.group4.group4androidcapstone.features.orders.data

interface OrderDataSource {
    suspend fun getOrders(): List<OrderLineItemDto>
}