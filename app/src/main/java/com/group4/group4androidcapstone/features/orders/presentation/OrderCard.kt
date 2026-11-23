package com.group4.group4androidcapstone.features.orders.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import com.group4.group4androidcapstone.features.orders.domain.Order

@Composable
fun OrderCard(
    order: Order,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .clearAndSetSemantics {
                contentDescription = "The date of order #${order.id} is " +
                        "${order.orderDate.toLocalDate()} and " +
                        "the status is ${displayOrderStatus(order.statusName)}." +
                        "Tap to view order details."
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Order #${order.id}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Date: ${order.orderDate.toLocalDate()}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Status: ${displayOrderStatus(order.statusName)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
