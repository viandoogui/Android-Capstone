package com.group4.group4androidcapstone.features.products.presentation.mapper

import com.group4.group4androidcapstone.features.products.domain.ProductStockCase

object ProductStockMapper {
    fun convertStockLevel(stockLevel: Int): ProductStockCase {
        return when {
            stockLevel > 10 -> ProductStockCase.IN_STOCK
            stockLevel == 0 -> ProductStockCase.OUT_OF_STOCK
            else -> ProductStockCase.UNKNOWN
        }
    }
}