package com.group4.group4androidcapstone.features.orders.presentation

import com.group4.group4androidcapstone.features.orders.domain.GetOrdersUseCase
import com.group4.group4androidcapstone.features.orders.domain.Order
import com.group4.group4androidcapstone.features.orders.domain.OrderRepository
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class OrderHistoryViewModelTest {

    @Test
    fun changingSortUpdatesSelection() {
        val repository = object : OrderRepository {
            override suspend fun getOrders(): List<Order> {
                return emptyList()
            }
        }

        val viewModel = OrderHistoryViewModel(
            GetOrdersUseCase(repository)
        )

        assertTrue(viewModel.uiState.value.newestFirst)

        viewModel.changeSort(false)
        assertFalse(viewModel.uiState.value.newestFirst)

        viewModel.changeSort(true)
        assertTrue(viewModel.uiState.value.newestFirst)
    }
}
