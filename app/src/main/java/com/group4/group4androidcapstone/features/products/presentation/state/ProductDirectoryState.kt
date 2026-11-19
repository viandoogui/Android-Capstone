package com.group4.group4androidcapstone.features.products.presentation.state

import com.group4.group4androidcapstone.features.products.domain.Product

data class ProductDirectoryState(
    val products: List<Product> = emptyList(),
    val searchQuery:String = "",
    val displayWarrantyOnly: Boolean = false,
    val showFilter: Boolean = false,
    val selectedProductId: Int? = null,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val currentSort: SortOrder = SortOrder.ASCENDING
) {
    val displayedProducts: List<Product>
        get() = products.filter {
            val matchesSearch = searchQuery.isBlank() || it.name.contains(searchQuery, ignoreCase = true)
            val matchesWarranty = !displayWarrantyOnly || !it.warranty.isNullOrEmpty()
            matchesSearch && matchesWarranty
        }

    val selectedProduct: Product?
        get() = products.find { it.productId == selectedProductId }

}

enum class SortOrder { ASCENDING, DESCENDING, PRICE_LOW_TO_HIGH }
