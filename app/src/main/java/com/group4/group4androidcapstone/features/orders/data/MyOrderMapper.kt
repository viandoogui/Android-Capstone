package com.group4.group4androidcapstone.features.orders.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.group4.group4androidcapstone.features.orders.domain.Order
import java.time.LocalDateTime
import com.group4.group4androidcapstone.features.orders.domain.LineItem

object MyOrderMapper {
    @RequiresApi(Build.VERSION_CODES.O)
    fun mapToDomain(response: MyOrderResponseDto, customerName: String): List<Order> {
        val firstName = customerName.substringBefore(' ')
        val lastName = customerName.substringAfter(' ', missingDelimiterValue = "")

        return response.orders.map { myOrderDto ->
            val orderDate = LocalDateTime.parse(myOrderDto.orderDate)
            val shipDate = myOrderDto.shipDate?.let { LocalDateTime.parse(it) } ?: orderDate

            Order(
                id = myOrderDto.orderNumber,
                customerId = response.customerId,
                firstName = firstName,
                lastName = lastName,
                orderDate = orderDate,
                shipDate = shipDate,
                items = myOrderDto.lines.map { line ->
                    LineItem(
                        id = line.lineId,
                        productName = line.productName ?: "(unknown product)",
                        orderQty = line.quantity,
                        unitPrice = line.unitPrice,
                        lineTotal = line.lineTotal
                    )
                }
            )
        }
    }
}