package com.group4.group4androidcapstone.features.products.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.group4.group4androidcapstone.core.room.AppDatabase
import com.group4.group4androidcapstone.testutil.builders.aProductEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.first
import org.junit.Test
import java.time.LocalDateTime

class ProductDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: ProductDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.productDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun givenExistingProduct_whenTimeUpdated_thenObservedAsRecent() = runTest {
        dao.upsertProducts(listOf(aProductEntity(productId = 1, name = "Pineapple")))
        dao.setNewProductViewedTime(1, LocalDateTime.now())

        dao.observeRecentProducts().test {
            val product = awaitItem().first()
            assertEquals(1, product.productId)
        }
    }

    @Test
    fun givenProductsWithTimes_whenObserved_thenListIsSortedRecentFirst() = runTest {
        val now = LocalDateTime.now()

        dao.upsertProducts(listOf(
            aProductEntity(productId = 1, name = "Pineapple", viewedTime = now.plusSeconds(4)),
            aProductEntity(productId = 2, name = "Apple", viewedTime = now.plusSeconds(10)),
            aProductEntity(productId = 3, name = "Strawberry", viewedTime = now.plusSeconds(1)),
            aProductEntity(productId = 4, name = "Taro", viewedTime = now.plusSeconds(6)),
            aProductEntity(productId = 5, name = "Amaranth", viewedTime = now.plusSeconds(7))
        ))

        dao.observeRecentProducts().test {
            val products = awaitItem()
            assertEquals(listOf(2, 5, 4, 1, 3), products.map { it.productId })
        }
    }

    @Test
    fun givenTenExistingViewedProducts_whenTimeOfNewProductUpdated_thenTimeOfOldestBecomesNull() = runTest {
        val now = LocalDateTime.now()

        dao.upsertProducts(listOf(
            aProductEntity(productId = 1, name = "Pineapple", viewedTime = now.plusSeconds(4)),
            aProductEntity(productId = 2, name = "Apple", viewedTime = now.plusSeconds(10)),
            aProductEntity(productId = 3, name = "Strawberry", viewedTime = now.plusSeconds(1)),
            aProductEntity(productId = 4, name = "Taro", viewedTime = now.plusSeconds(6)),
            aProductEntity(productId = 5, name = "Amaranth", viewedTime = now.plusSeconds(7)),
            aProductEntity(productId = 6, name = "Pepperoni", viewedTime = now.plusSeconds(3)),
            aProductEntity(productId = 7, name = "Italian Cheese", viewedTime = now.plusSeconds(5)),
            aProductEntity(productId = 8, name = "Zesty Marinara", viewedTime = now.plusSeconds(2)),
            aProductEntity(productId = 9, name = "Zucchini", viewedTime = now.plusSeconds(8)),
            aProductEntity(productId = 10, name = "Anchovies", viewedTime = now.plusSeconds(9)),
            aProductEntity(productId = 11, name = "Salsa")
        ))

        dao.setNewProductViewedTime(11, now.plusSeconds(11))

        dao.observeRecentProducts().test {
            assertEquals(false, awaitItem().map { it.productId }.contains(3))
        }
    }

    @Test
    fun givenProductViewedTimeCleared_whenRecentObserved_thenListIsEmpty() = runTest {
        dao.upsertProducts(listOf(aProductEntity(productId = 1, name = "Pineapple", viewedTime = LocalDateTime.now())))
        dao.upsertProducts(listOf(aProductEntity(productId = 2, name = "Apple", viewedTime = LocalDateTime.now())))
        dao.upsertProducts(listOf(aProductEntity(productId = 3, name = "Strawberry", viewedTime = LocalDateTime.now())))

        dao.observeRecentProducts().test {
            dao.clearAllProductViewedTimes()
            assertEquals(emptyList<ProductEntity>(), awaitItem())
        }
    }

    @Test
    fun givenEmptyTable_whenObserved_thenEmptyListEmitted() = runTest {
        dao.observeProducts().test {
            assertEquals(emptyList<ProductEntity>(), awaitItem())
        }
    }

    @Test
    fun givenObservedTable_whenProductsInserted_thenNewListEmitted() = runTest {
        dao.observeProducts().test {
            assertEquals(emptyList<ProductEntity>(), awaitItem())

            dao.upsertProducts(listOf(aProductEntity(productId = 1), aProductEntity(productId = 2)))

            val productIds = awaitItem().map { it.productId }.sorted()

            assertEquals(listOf(1, 2), productIds)
        }
    }

    @Test
    fun givenExistingProduct_whenProductWithSameProductIdInserted_thenProductReplaced() = runTest {
        dao.upsertProducts(listOf(aProductEntity(productId = 1, name = "Pineapple")))
        dao.upsertProducts(listOf(aProductEntity(productId = 1, name = "Apple")))

        val products = dao.observeProducts().first()

        assertEquals(listOf("Apple"), products.map { it.name })
    }

    @Test
    fun givenExistingProducts_whenReplaceAll_thenUpdateAndDeleteBothOccur() = runTest {
        dao.upsertProducts(listOf(
            aProductEntity(productId = 1, name = "Frisbee"),
            aProductEntity(productId = 2, name = "Basketball")
        ))

        dao.replaceAllProducts(listOf(
            aProductEntity(productId = 1, name = "Frisbee 2"),
            aProductEntity(productId = 3, name = "Basketball")
        ))

        val products = dao.observeProducts().first().sortedBy { it.productId }

        assertEquals(listOf(1, 3), products.map { it.productId })

        assertEquals(listOf("Frisbee 2", "Basketball"), products.map { it.name })
    }

    @Test
    fun givenNonExistentProductId_whenObservedById_thenEmitsNull() = runTest {
        dao.observeProduct(100).test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun givenExistingProduct_whenObserved_thenEmitsThatProduct() = runTest {
        val product = aProductEntity(productId = 1, name = "Product 1")
        dao.upsertProducts(listOf(product))
        val result = dao.observeProduct(1).first()
        assertEquals(product, result)
    }

    @Test
    fun givenObservedProduct_whenDifferentProductAdded_thenOldProductUnchanged() = runTest {
        dao.upsertProducts(listOf(aProductEntity(productId = 1, name = "Product 1")))

        dao.observeProduct(1).test {
            val first = awaitItem()
            dao.upsertProducts(listOf(aProductEntity(productId = 2, name = "Product 2")))
            assertEquals(first, awaitItem())
        }
    }

}