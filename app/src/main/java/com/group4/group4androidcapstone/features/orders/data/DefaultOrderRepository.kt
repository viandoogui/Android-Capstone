package com.group4.group4androidcapstone.features.orders.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.group4.group4androidcapstone.features.orders.domain.OrderRepository
import com.group4.group4androidcapstone.features.orders.domain.Order
import javax.inject.Inject

class DefaultOrderRepository @Inject constructor(
    private val dataSource: OrderDataSource,
): OrderRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getOrders(): List<Order> {
        val data = dataSource.getOrders()
        val orders = OrderMapper.mapToDomain(data)

        return orders.filter { it.customerId == 11501 }
    }
}