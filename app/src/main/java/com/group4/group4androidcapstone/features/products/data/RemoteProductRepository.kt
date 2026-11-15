package com.group4.group4androidcapstone.features.products.data

import com.group4.group4androidcapstone.features.products.data.local.ProductDao
import com.group4.group4androidcapstone.features.products.data.local.ProductDetailsEntityMapper
import com.group4.group4androidcapstone.features.products.data.local.ProductEntityMapper
import com.group4.group4androidcapstone.features.products.domain.Product
import com.group4.group4androidcapstone.features.products.domain.ProductDetails
import com.group4.group4androidcapstone.features.products.domain.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private val dataSource: ProductDataSource,
): ProductRepository {
    override fun observeProducts(): Flow<List<Product>> =
        productDao.observeProducts().map { list ->
            list.map { entity ->
                ProductEntityMapper.toDomain(entity)
            }
        }

    override fun observeRecentProducts(): Flow<List<Product>> =
        productDao.observeRecentProducts().map { list ->
            list.map { entity ->
                ProductEntityMapper.toDomain(entity)
            }.sortedBy { it.viewedTime }
        }

    override fun observeProductDetails(id: Int): Flow<ProductDetails?> =
        productDao.observeDetail(id).map { entity ->
            entity?.let {
                ProductDetailsEntityMapper.toDomain(it)
            }

        }

    override suspend fun refreshProducts() {
        val entities = dataSource.getProducts().map {
            ProductEntityMapper.toEntity(it)
        }

        productDao.replaceAllProducts(entities)
    }

    override suspend fun refreshProductDetails(id: Int): Unit {
        val entity = ProductDetailsEntityMapper.toEntity(dataSource.getProductDetails(id))
        productDao.upsertDetails(listOf(entity))
    }

    override suspend fun setProductViewedTime(id: Int, viewedTime: LocalDateTime) {
        val entity = productDao.observeProduct(id).first()

        if(entity != null) {
            productDao.setNewProductViewedTime(id, viewedTime)
        }
    }
}