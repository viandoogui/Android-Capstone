package com.group4.group4androidcapstone.features.orders.data

interface MyOrdersDataSource {
    suspend fun getOrders(customerId: Int, page: Int, pageSize: Int): MyOrderResponseDto
}