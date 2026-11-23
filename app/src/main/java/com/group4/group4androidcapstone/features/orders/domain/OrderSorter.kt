package com.group4.group4androidcapstone.features.orders.domain


object OrderSorter {

    fun newestFirst(orders: List<Order>): List<Order> {
        return orders.sortedByDescending { it.orderDate }
    }

    fun oldestFirst(orders: List<Order>): List<Order> {
        return orders.sortedBy { it.orderDate }
    }
}
