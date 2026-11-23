package com.group4.group4androidcapstone.features.orders.domain

interface OrderRepository {
    suspend fun getOrders(): List<Order>
}