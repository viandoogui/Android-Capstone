package com.group4.group4androidcapstone.features.orders.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.group4.group4androidcapstone.features.orders.domain.LineItem
import com.group4.group4androidcapstone.features.orders.domain.Order
import com.group4.group4androidcapstone.ui.theme.LocalSpacing
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun OrderDetailsScreen(
    order: Order,
    onBack: () -> Unit = {}
) {
    val customerName = "${order.firstName.orEmpty()} ${order.lastName.orEmpty()}".trim()
    val displayStatus = displayOrderStatus(order.statusName)
    val itemsSubtotal = calculateItemsSubtotal(order.items)
    val shippingDate = order.shipDate?.toLocalDate()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
        contentColor = Color.Black,
        shape = RoundedCornerShape(LocalSpacing.current.small)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            Button(
                onClick = onBack,
                modifier = Modifier.semantics {
                    contentDescription = "Click to go back to the order history screen"
                }
            ) {
                Text("Back to Order History")
            }

            Text(
                text = "Order Details",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .clearAndSetSemantics {
                        contentDescription = "Order #${order.id}. " +
                                "Customer: $customerName. " +
                                "Ordered on ${formatLocalDate(order.orderDate.toLocalDate())}. " +
                                "Status: $displayStatus. " +
                                when {
                                    shippingDate == null -> "Shipping date unavailable."

                                    displayStatus == "Shipped" ->
                                        "Shipped on ${formatLocalDate(shippingDate)}."

                                    else ->
                                        "Shipping date on record: ${formatLocalDate(shippingDate)}. " +
                                                "This does not confirm shipment."
                                }
                    }
                    .fillMaxWidth()
            ) {

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
                when {
                    shippingDate == null -> {
                        Text("Shipping date unavailable")
                    }

                    displayStatus == "Shipped" -> {
                        Text("Shipped on: $shippingDate")
                    }

                    else -> {
                        Text("Shipping date on record: $shippingDate")
                        Text(
                            text = "This date does not confirm shipment.",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Text(
                text = "Products",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            if (order.items.isEmpty()) {
                Text("Product details unavailable")
            } else {
                order.items.forEach { item ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .clearAndSetSemantics {
                                contentDescription =
                                    "Product ${item.productName ?: "with no name"}" +
                                            "has a quantity of ${item.orderQty}, a unit price of ${
                                                formatOrderMoney(
                                                    item.unitPrice
                                                )
                                            }," +
                                            "and a line total of ${formatOrderMoney(item.lineTotal)}"
                            }
                            .fillMaxWidth()
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
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .semantics {
                            contentDescription = "The subtotal of all products in this order is " +
                                    formatOrderMoney(itemsSubtotal)
                        }
                        .fillMaxWidth()
                )
            }
        }
    }
}

internal fun calculateItemsSubtotal(items: List<LineItem>): BigDecimal {
    var subtotal = BigDecimal.ZERO

    items.forEach { item ->
        subtotal = subtotal.add(item.lineTotal)
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

internal fun formatOrderMoney(value: BigDecimal): String {
    val amount = value.setScale(2, RoundingMode.HALF_UP)
    return "$" + amount.toPlainString()
}

private fun formatLocalDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    return date.format(formatter)
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
                unitPrice = BigDecimal("24.99"),
                lineTotal = BigDecimal("49.98")
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
