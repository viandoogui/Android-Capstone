package com.group4.group4androidcapstone.features.products.data

import com.group4.group4androidcapstone.features.products.domain.Product
import com.group4.group4androidcapstone.features.products.domain.ProductDetails
import com.group4.group4androidcapstone.features.products.domain.ProductRepository
import javax.inject.Inject

class RemoteProductRepository @Inject constructor(
    private val dataSource: ProductDataSource,
): ProductRepository {

    override suspend fun getProducts(): List<Product> {
        val response = dataSource.getProducts()
        val products = response.map {
            ProductMapper.mapToDomain(it)
        }
        return products
    }

    override suspend fun getProductDetails(id: Int): ProductDetails {
        val response = dataSource.getProductDetails(id)
        return ProductDetailsMapper.mapToDomain(response)
    }
}