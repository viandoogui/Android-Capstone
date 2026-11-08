package com.group4.group4androidcapstone.features.orders.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.group4.group4androidcapstone.features.orders.domain.Order
import com.group4.group4androidcapstone.features.orders.domain.OrderRepository
import javax.inject.Inject

class MyOrdersRepository @Inject constructor(
    private val dataSource: MyOrdersDataSource,
): OrderRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getOrders(): List<Order> {
        val response = dataSource.getOrders(11501, 1, 100)
        return MyOrderMapper.mapToDomain(response, "Brandy Chandra")
    }
}