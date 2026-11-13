package com.group4.group4androidcapstone.features.orders.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.group4.group4androidcapstone.features.orders.domain.Order
import java.time.LocalDateTime
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import com.group4.group4androidcapstone.features.orders.domain.OrderSorter

@Composable
fun OrderHistoryScreen(
    uiState: OrderHistoryUiState,
    onSortChange: (Boolean) -> Unit
) {

    when {
        uiState.isLoading -> Text("Loading orders...")

        uiState.errorMessage != null -> Text(uiState.errorMessage)

        uiState.orders.isEmpty() -> Text("No orders found")


        else -> LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Order History",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = { onSortChange(true) }) {
                        Text("Newest First")
                    }

                    Button(onClick = { onSortChange(false) }) {
                        Text("Oldest First")
                    }
                }
            }

            items(items = uiState.orders) { order ->
                Column {
                    Text("Order #${order.id}")
                    Text("Date: ${order.orderDate.toLocalDate()}")
                    Text("Status: ${order.statusName}")
                }
            }
        }
    }
}

@Composable
fun OrderHistoryRoute(
    viewModel: OrderHistoryViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadOrders()
    }

    OrderHistoryScreen(
        uiState = state.value,
        onSortChange = { newestFirst ->
            viewModel.changeSort(newestFirst)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun OrderHistoryPreview() {
    OrderHistoryScreen(
        uiState = OrderHistoryUiState(isLoading = true),
        onSortChange = {}
    )
}

@Preview(showBackground = true)
@Composable
fun OrderHistoryListPreview() {
    val newestFirst = remember { mutableStateOf(true) }
    val olderOrder = Order(
        id = 101,
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
        id = 102,
        orderDate = LocalDateTime.of(2026, 11, 10, 0, 0),
        statusName = "Shipped"
    )

    OrderHistoryScreen(
        uiState = OrderHistoryUiState(
            orders = if (newestFirst.value) {
                OrderSorter.newestFirst(listOf(olderOrder, newerOrder))
            } else {
                OrderSorter.oldestFirst(listOf(olderOrder, newerOrder))
            },
            newestFirst = newestFirst.value
        ),
        onSortChange = { newestFirst.value = it }
    )
}

@Preview(showBackground = true)
@Composable
fun OrderHistoryEmptyPreview() {
    OrderHistoryScreen(
        uiState = OrderHistoryUiState(),
        onSortChange = {}
    )
}

@Preview(showBackground = true)
@Composable
fun OrderHistoryErrorPreview() {
    OrderHistoryScreen(
        uiState = OrderHistoryUiState(
            errorMessage = "Unable to load orders"
        ),
        onSortChange = {}
    )
}
