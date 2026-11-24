package com.group4.group4androidcapstone.features.orders.data

import java.math.BigDecimal
data class OrderLineItemDto (
    val id: Int,
    val customerId: Int,
    val firstName: String?,
    val lastName: String?,
    val orderDate: String,
    val shipDate: String?,
    val productName: String?,
    val orderNumber: Int,
    val orderQty: Int,
    val unitPrice: BigDecimal,
    val lineTotal: BigDecimal,
    val status: Int,
    val statusName: String
)