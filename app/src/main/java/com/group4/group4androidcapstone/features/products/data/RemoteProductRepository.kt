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

@Singleton class RemoteProductRepository @Inject constructor(
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
            }
        }

    override fun observeSavedProducts(): Flow<List<Product>> =
        productDao.observeSavedProducts().map { list ->
            list.map { entity ->
                ProductEntityMapper.toDomain(entity)
            }
        }

    override fun observeProductDetails(id: Int): Flow<ProductDetails?> =
        productDao.observeDetail(id).map { entity ->
            entity?.let {
                ProductDetailsEntityMapper.toDomain(it)
            }

        }

    override suspend fun refreshProducts() {
        val oldEntities = productDao.getProducts().associateBy { it.productId }

        val newEntities = dataSource.getProducts().map {
            val oldEntity = oldEntities[it.productId]
            ProductEntityMapper.toEntity(it).copy(
                viewedTime = oldEntity?.viewedTime, // copy over new fields but retain time
                isSaved = oldEntity?.isSaved == true
            )
        }

        productDao.replaceAllProducts(newEntities)
    }

    override suspend fun refreshProductDetails(id: Int): Unit {
        val entity = ProductDetailsEntityMapper.toEntity(dataSource.getProductDetails(id))
        productDao.upsertDetails(listOf(entity))
    }

    override suspend fun setProductViewedTime(id: Int, viewedTime: LocalDateTime?) {
        val entity = productDao.observeProduct(id).first()

        if(entity != null) {
            productDao.setNewProductViewedTime(id, viewedTime)
        }
    }

    override suspend fun clearProductViewedTimes() {
        productDao.clearAllProductViewedTimes()
    }

    override suspend fun setProductSaved(id: Int, isSaved: Boolean) {
        val savedTime = if (isSaved) LocalDateTime.now() else null
        productDao.setProductSaved(id, isSaved, savedTime)
    }

}