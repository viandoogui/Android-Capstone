package com.group4.group4androidcapstone.features.products.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    // Products

    @Query("SELECT * FROM products")
    fun observeProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE productId = :id")
    fun observeProduct(id: Int): Flow<ProductEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProducts(products: List<ProductEntity>)

    @Query("DELETE FROM products WHERE productId NOT IN (:ids)")
    suspend fun deleteNotInProducts(ids: List<Int>)

    @Transaction
    suspend fun replaceAllProducts(products: List<ProductEntity>) {
        upsertProducts(products)
        deleteNotInProducts(products.map {it.productId})
    }

    // ProductDetails

    @Query("SELECT * FROM product_details WHERE productId = :id")
    fun observeDetail(id: Int): Flow<ProductDetailsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDetails(products: List<ProductDetailsEntity>)

    @Query("DELETE FROM product_details WHERE productId NOT IN (:ids)")
    suspend fun deleteNotInDetails(ids: List<Int>)

    @Transaction
    suspend fun replaceAllDetails(productDetails: List<ProductDetailsEntity>) {
        upsertDetails(productDetails)
        deleteNotInDetails(productDetails.map {it.productId})
    }

}