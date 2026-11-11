package com.group4.group4androidcapstone.features.products.presentation.state

import com.group4.group4androidcapstone.features.products.domain.Product

data class ProductDirectoryState(
    val products: List<Product> = emptyList(),
    val searchQuery:String = "",
    val summaryFilter: String = "",
    val showFilter: Boolean = false,
    val selectedProductId: Int? = null,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
) {
    val displayedProducts: List<Product>
        get() = products.filter {
            val matchesSearch = searchQuery.isBlank() || it.name.contains(searchQuery, ignoreCase = true)
            val matchesSummary = summaryFilter == "" || it.summary.contains(summaryFilter, ignoreCase = true)

            matchesSearch && matchesSummary
        }

    val selectedProduct: Product?
        get() = products.find { it.productId == selectedProductId }

}