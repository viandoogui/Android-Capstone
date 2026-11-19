package com.group4.group4androidcapstone.features.HomeScreen.presentation.state

import com.group4.group4androidcapstone.features.orders.domain.Order
import com.group4.group4androidcapstone.features.products.domain.Product

data class HomeUiState(
    val recentProducts: List<Product> = emptyList(),
    val recentOrders: List<Order> = emptyList(),
    val isRefreshing: Boolean = false,
   val error: String? = null
)