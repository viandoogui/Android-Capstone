package com.group4.group4androidcapstone.features.orders.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.group4.group4androidcapstone.features.orders.domain.LineItem
import com.group4.group4androidcapstone.features.orders.domain.Order
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

object OrderMapper {
    @RequiresApi(Build.VERSION_CODES.O)
    fun mapToDomain(items: List<OrderLineItemDto>): List<Order> {
        val orderGroupMap = items.groupBy { it.orderNumber }
        return orderGroupMap.map { (orderNumber, orderLineItems) ->
            val firstLine = orderLineItems.first()

            val shipDate = try {
                firstLine.shipDate?.let {
                    LocalDateTime.parse(it)
                }
            } catch (e: DateTimeParseException) {
                null
            }

            Order(
                id = orderNumber,
                customerId = firstLine.customerId,
                firstName = firstLine.firstName,
                lastName = firstLine.lastName,
                orderDate = LocalDateTime.parse(firstLine.orderDate),
                shipDate = shipDate,
                status = firstLine.status,
                statusName = firstLine.statusName,
                items = orderLineItems.map { lineItem ->
                    LineItem(
                        id = lineItem.id,
                        productName = lineItem.productName,
                        orderQty = lineItem.orderQty,
                        unitPrice = lineItem.unitPrice,
                        lineTotal = lineItem.lineTotal

                    )

                }
            )
        }
    }
}