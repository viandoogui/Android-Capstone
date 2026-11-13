package com.group4.group4androidcapstone.features.products.presentation.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable

fun ProductDetailText(
    label: String,
    value: Any?
) {
    value?.takeIf { it.toString().isNotBlank() && it != 0.0 && it != 0 }?.let {
        Text(
            "$label: $it",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}