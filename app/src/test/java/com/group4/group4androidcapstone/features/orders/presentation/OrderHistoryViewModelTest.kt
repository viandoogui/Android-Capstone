package com.group4.group4androidcapstone.features.orders.presentation

import com.group4.group4androidcapstone.features.orders.domain.GetOrdersUseCase
import com.group4.group4androidcapstone.features.orders.domain.Order
import com.group4.group4androidcapstone.features.orders.domain.OrderRepository
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import com.group4.group4androidcapstone.testutil.MainDispatcherRule
import org.junit.Rule
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import java.io.IOException

class OrderHistoryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

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

    @Test
    fun loadingOrdersUpdatesSuccessState() = runTest {
        val order = Order(
            id = 101,
            customerId = 11501,
            firstName = "Brandy",
            lastName = "Chandra",
            orderDate = java.time.LocalDateTime.of(2026, 11, 1, 0, 0),
            shipDate = java.time.LocalDateTime.of(2026, 11, 2, 0, 0),
            status = 1,
            statusName = "Pending",
            items = emptyList()
        )

        val repository = object : OrderRepository {
            override suspend fun getOrders(): List<Order> {
                return listOf(order)
            }
        }

        val viewModel = OrderHistoryViewModel(
            GetOrdersUseCase(repository)
        )

        viewModel.uiState.test {
            assertEquals(OrderHistoryUiState(), awaitItem())

            viewModel.loadOrders()

            assertTrue(awaitItem().isLoading)

            val result = awaitItem()
            assertFalse(result.isLoading)
            assertEquals(101, result.orders.first().id)
        }
    }

    @Test
    fun loadingOrdersUpdatesErrorState() = runTest {
        val repository = object : OrderRepository {
            override suspend fun getOrders(): List<Order> {
                throw IOException("network down")
            }
        }

        val viewModel = OrderHistoryViewModel(
            GetOrdersUseCase(repository)
        )

        viewModel.uiState.test {
            assertEquals(OrderHistoryUiState(), awaitItem())

            viewModel.loadOrders()

            assertTrue(awaitItem().isLoading)

            val result = awaitItem()
            assertFalse(result.isLoading)
            assertEquals("Unable to load orders", result.errorMessage)
        }
    }
}