package com.group4.group4androidcapstone.features.orders.presentation

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.group4.group4androidcapstone.features.orders.domain.Order
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDateTime
import com.group4.group4androidcapstone.features.orders.domain.LineItem
import java.math.BigDecimal
import java.math.RoundingMode
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button

@Composable
fun OrderDetailsScreen(
    order: Order,
    onBack: () -> Unit = {}
) {
    val customerName = "${order.firstName.orEmpty()} ${order.lastName.orEmpty()}".trim()
    val displayStatus = displayOrderStatus(order.statusName)
    val itemsSubtotal = calculateItemsSubtotal(order.items)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Button(onClick = onBack) {
            Text("Back to Order History")
        }

        Text(
            text = "Order Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Order #${order.id}",
            style = MaterialTheme.typography.titleMedium
        )

        if (customerName.isNotEmpty()) {
            Text(
                text = "Customer: $customerName"
            )
        }

        Text(
            text = "Date: ${order.orderDate.toLocalDate()}"
        )

        Text(
            text = "Status: $displayStatus"
        )
        if (displayStatus == "Shipped") {
            Text(
                text = "Shipped on: ${order.shipDate.toLocalDate()}"
            )
        } else {
            Text(
                text = "Shipping date on record: ${order.shipDate.toLocalDate()}"
            )
            Text(
                text = "This date does not confirm shipment.",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Text(
            text = "Products",
            style = MaterialTheme.typography.titleMedium
        )

        if (order.items.isEmpty()) {
            Text("Product details unavailable")
        } else {
            order.items.forEach { item ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = item.productName ?: "Product name unavailable",
                        style = MaterialTheme.typography.titleSmall
                    )

                    Text("Quantity: ${item.orderQty}")

                    Text("Unit Price: ${formatOrderMoney(item.unitPrice)}")

                    Text("Line Total: ${formatOrderMoney(item.lineTotal)}")
                }
            }
        }

        if (order.items.isNotEmpty()) {
            Text(
                text = "Items Subtotal: ${formatOrderMoney(itemsSubtotal)}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

internal fun calculateItemsSubtotal(items: List<LineItem>): BigDecimal {
    var subtotal = BigDecimal.ZERO

    items.forEach { item ->
        subtotal = subtotal.add(
            BigDecimal.valueOf(item.lineTotal)
        )
    }

    return subtotal
}
internal fun displayOrderStatus(statusName: String): String {
    return when (statusName.trim().lowercase()) {
        "pending" -> "Pending"
        "in process", "processing" -> "Processing"
        "approved" -> "Approved"
        "backordered" -> "Backordered"
        "rejected" -> "Rejected"
        "shipped" -> "Shipped"
        "cancelled", "canceled" -> "Cancelled"
        else -> "Unknown Status"
    }
}

private fun formatOrderMoney(value: Double): String {
    val amount = BigDecimal.valueOf(value)
        .setScale(2, RoundingMode.HALF_UP)
    return "$" + amount.toPlainString()
}

private fun formatOrderMoney(value: BigDecimal): String {
    val amount = value.setScale(2, RoundingMode.HALF_UP)
    return "$" + amount.toPlainString()
}

@Preview(showBackground = true)
@Composable
fun OrderDetailsPreview() {
    val sampleOrder = Order(
        id = 101,
        customerId = 11501,
        firstName = "Brandy",
        lastName = "Chandra",
        orderDate = LocalDateTime.of(2026, 11, 1, 0, 0),
        shipDate = LocalDateTime.of(2026, 11, 2, 0, 0),
        status = 1,
        statusName = "Pending",
        items = listOf(
            LineItem(
                id = 1,
                productName = "Sample Product",
                orderQty = 2,
                unitPrice = 24.99,
                lineTotal = 49.98
            )
        )
    )

    OrderDetailsScreen(order = sampleOrder)
}

@Preview(showBackground = true)
@Composable
fun OrderDetailsMissingProductsPreview() {
    val sampleOrder = Order(
        id = 102,
        customerId = 11501,
        firstName = "Brandy",
        lastName = "Chandra",
        orderDate = LocalDateTime.of(2026, 11, 10, 0, 0),
        shipDate = LocalDateTime.of(2026, 11, 11, 0, 0),
        status = 1,
        statusName = "Pending",
        items = emptyList()
    )

    OrderDetailsScreen(order = sampleOrder)
}
