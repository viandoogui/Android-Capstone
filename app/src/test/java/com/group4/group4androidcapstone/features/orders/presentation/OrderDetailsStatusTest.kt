package com.group4.group4androidcapstone.features.orders.presentation

import org.junit.Test
import org.junit.Assert.assertEquals

class OrderDetailsStatusTest {

    @Test
    fun knownStatusDisplaysCorrectly() {
        val result = displayOrderStatus("Shipped")
        assertEquals("Shipped", result)
    }

    @Test
    fun unknownStatusShowsFallback() {
        val result = displayOrderStatus("UnexpectedCode")
        assertEquals("Unknown Status", result)
    }

    @Test
    fun emptyStatusShowsFallback() {
        val result = displayOrderStatus("")
        assertEquals("Unknown Status", result)
    }
}
