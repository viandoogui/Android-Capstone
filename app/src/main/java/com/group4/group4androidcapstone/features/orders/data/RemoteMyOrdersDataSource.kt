package com.group4.group4androidcapstone.features.products.data

import com.group4.group4androidcapstone.features.orders.data.MyOrderResponseDto
import com.group4.group4androidcapstone.features.orders.data.MyOrdersDataSource
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteMyOrdersDataSource: MyOrdersDataSource {

    @GET("my/customers/{customerId}/orders")
    override suspend fun getOrders(
        @Path("customerId") customerId: Int,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): MyOrderResponseDto
}