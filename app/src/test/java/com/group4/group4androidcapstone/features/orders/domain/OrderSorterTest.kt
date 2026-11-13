package com.group4.group4androidcapstone.features.orders.domain

import org.junit.Test
import org.junit.Assert.assertEquals
import java.time.LocalDateTime

class OrderSorterTest {

    @Test
    fun newestOrdersComeFirst() {
        val olderOrder = Order(
            id = 1,
            customerId = 11501,
            firstName = "Brandy",
            lastName = "Chandra",
            orderDate = LocalDateTime.of(2026, 11, 1, 0, 0),
            shipDate = LocalDateTime.of(2026, 11, 2, 0, 0),
            status = 1,
            statusName = "Pending",
            items = emptyList()
        )

        val newerOrder = olderOrder.copy(
            id = 2,
            orderDate = LocalDateTime.of(2026, 11, 10, 0, 0)
        )

        val result = OrderSorter.newestFirst(
            listOf(olderOrder, newerOrder)
        )

        assertEquals(listOf(2, 1), result.map { it.id })
    }

    @Test
    fun oldestOrdersComeFirst() {
        val olderOrder = Order(
            id = 1,
            customerId = 11501,
            firstName = "Brandy",
            lastName = "Chandra",
            orderDate = LocalDateTime.of(2026, 11, 1, 0, 0),
            shipDate = LocalDateTime.of(2026, 11, 2, 0, 0),
            status = 1,
            statusName = "Pending",
            items = emptyList()
        )

        val newerOrder = olderOrder.copy(
            id = 2,
            orderDate = LocalDateTime.of(2026, 11, 10, 0, 0)
        )

        val result = OrderSorter.oldestFirst(
            listOf(newerOrder, olderOrder)
        )

        assertEquals(listOf(1, 2), result.map { it.id })
    }

}
