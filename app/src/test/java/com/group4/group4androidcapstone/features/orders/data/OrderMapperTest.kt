package com.group4.group4androidcapstone.features.orders.data

import org.junit.Test
import org.junit.Assert.assertEquals
import java.math.BigDecimal
import org.junit.Assert.assertNull

class OrderMapperTest {

    @Test
    fun unknownOrderStatusIsHandled() {
        val orderLine = OrderLineItemDto(
            id = 1,
            customerId = 11501,
            firstName = "Brandy",
            lastName = "Chandra",
            orderDate = "2026-11-01T00:00:00",
            shipDate = "2026-11-02T00:00:00",
            productName = "Sample Product",
            orderNumber = 101,
            orderQty = 1,
            unitPrice = BigDecimal("10.00"),
            lineTotal = BigDecimal("10.00"),
            status = 999,
            statusName = "Unknown"
        )

        val orders = OrderMapper.mapToDomain(listOf(orderLine))

        assertEquals(1, orders.size)
        assertEquals(999, orders.first().status)
        assertEquals("Unknown", orders.first().statusName)
    }

    @Test
    fun missingShippingDateIsHandled() {
        val orderLine = OrderLineItemDto(
            id = 1,
            customerId = 11501,
            firstName = "Brandy",
            lastName = "Chandra",
            orderDate = "2026-11-01T00:00:00",
            shipDate = null,
            productName = "Sample Product",
            orderNumber = 101,
            orderQty = 1,
            unitPrice = BigDecimal("10.00"),
            lineTotal = BigDecimal("10.00"),
            status = 1,
            statusName = "Pending"
        )

        val orders = OrderMapper.mapToDomain(listOf(orderLine))

        assertEquals(1, orders.size)
        assertNull(orders.first().shipDate)
    }

    @Test
    fun invalidShippingDateIsHandled() {
        val orderLine = OrderLineItemDto(
            id = 1,
            customerId = 11501,
            firstName = "Brandy",
            lastName = "Chandra",
            orderDate = "2026-11-01T00:00:00",
            shipDate = "invalid-date",
            productName = "Sample Product",
            orderNumber = 101,
            orderQty = 1,
            unitPrice = BigDecimal("10.00"),
            lineTotal = BigDecimal("10.00"),
            status = 1,
            statusName = "Pending"
        )

        val orders = OrderMapper.mapToDomain(listOf(orderLine))

        assertEquals(1, orders.size)
        assertNull(orders.first().shipDate)
    }
}
