package com.group4.group4androidcapstone.features.orders.presentation

import com.group4.group4androidcapstone.features.orders.domain.Order

data class OrderHistoryUiState(
    val orders: List<Order> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val newestFirst: Boolean = true
)