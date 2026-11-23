package com.group4.group4androidcapstone.features.orders.domain

import java.math.BigDecimal
data class LineItem(
    val id: Int,
    val productName: String?,
    val orderQty: Int,
    val unitPrice: BigDecimal,
    val lineTotal: BigDecimal
)

