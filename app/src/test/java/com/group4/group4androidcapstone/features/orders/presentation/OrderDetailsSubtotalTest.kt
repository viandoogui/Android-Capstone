package com.group4.group4androidcapstone.features.orders.presentation

import com.group4.group4androidcapstone.features.orders.domain.LineItem
import org.junit.Test
import org.junit.Assert.assertEquals
import java.math.BigDecimal

class OrderDetailsSubtotalTest {

    @Test
    fun subtotalAddsAllLineTotals() {
        val items = listOf(
            LineItem(
                id = 1,
                productName = "Product A",
                orderQty = 2,
                unitPrice = BigDecimal("24.99"),
                lineTotal = BigDecimal("49.98")
            ),
            LineItem(
                id = 2,
                productName = "Product B",
                orderQty = 1,
                unitPrice = BigDecimal("10.50"),
                lineTotal = BigDecimal("10.50")
            )
        )

        val result = calculateItemsSubtotal(items)

        assertEquals(BigDecimal("60.48"), result)
    }

    @Test
    fun emptyItemsHaveZeroSubtotal() {
        val result = calculateItemsSubtotal(emptyList())

        assertEquals(BigDecimal.ZERO, result)
    }
}
