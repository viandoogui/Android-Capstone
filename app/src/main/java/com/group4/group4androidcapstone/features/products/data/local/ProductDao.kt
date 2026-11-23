package com.group4.group4androidcapstone.features.products.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime

@Dao
interface ProductDao {

    // Products

    @Query("SELECT * FROM products")
    suspend fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM products")
    fun observeProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE productId = :id")
    fun observeProduct(id: Int): Flow<ProductEntity?>

    @Query("SELECT * FROM products WHERE viewedTime IS NOT NULL ORDER BY viewedTime DESC")
    fun observeRecentProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE is_saved = '1' ORDER BY savedTime DESC")
    fun observeSavedProducts(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProducts(products: List<ProductEntity>)

    @Query("DELETE FROM products WHERE productId NOT IN (:ids)")
    suspend fun deleteNotInProducts(ids: List<Int>)

    @Transaction
    suspend fun replaceAllProducts(products: List<ProductEntity>) {
        upsertProducts(products)
        deleteNotInProducts(products.map {it.productId})
    }

    @Query("UPDATE products SET viewedTime = :viewedTime WHERE productId = :id")
    suspend fun updateProductViewedTime(id: Int, viewedTime: LocalDateTime?)

    @Transaction
    suspend fun setNewProductViewedTime(id: Int, viewedTime: LocalDateTime?){
        updateProductViewedTime(id, viewedTime)

        val entities = observeRecentProducts().first()
        if(entities.count() > 10) {
            entities.drop(10).forEach {
                updateProductViewedTime(it.productId, null)
            }
        }
    }

    @Query("UPDATE products SET viewedTime = NULL")
    suspend fun clearAllProductViewedTimes()

    @Query("UPDATE products SET is_saved = :isSaved, savedTime = :savedTime WHERE productId = :id")
    suspend fun setProductSaved(id: Int, isSaved: Boolean, savedTime: LocalDateTime?)

    @Transaction

    // ProductDetails

    @Query("SELECT * FROM product_details")
    fun observeDetails(): Flow<List<ProductDetailsEntity>>

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