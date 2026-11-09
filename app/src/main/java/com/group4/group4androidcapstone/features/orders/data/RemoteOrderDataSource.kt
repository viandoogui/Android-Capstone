package com.group4.group4androidcapstone.features.orders.data

import retrofit2.http.GET

interface RemoteOrderDataSource: OrderDataSource {

    @GET("Order/customer")
    override suspend fun getOrders(): List<OrderLineItemDto>
}