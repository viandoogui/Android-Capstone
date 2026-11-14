package com.group4.group4androidcapstone.features.products.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.group4.group4androidcapstone.core.room.AppDatabase
import com.group4.group4androidcapstone.testutil.builders.aProductDetailsEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.first
import org.junit.Test

class ProductDetailsDaoTest {
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
    fun givenEmptyTable_whenObserved_thenEmptyListEmitted() = runTest {
        dao.observeDetails().test {
            assertEquals(emptyList<ProductDetailsEntity>(), awaitItem())
        }
    }

    @Test
    fun givenObservedTable_whenProductDetailsInserted_thenNewListEmitted() = runTest {
        dao.observeDetails().test {
            assertEquals(emptyList<ProductDetailsEntity>(), awaitItem())

            dao.upsertDetails(listOf(aProductDetailsEntity(productId = 1), aProductDetailsEntity(productId = 2)))

            val productIds = awaitItem().map { it.productId }.sorted()

            assertEquals(listOf(1, 2), productIds)
        }
    }

    @Test
    fun givenExistingProductDetail_whenProductDetailWithSameProductIdInserted_thenDetailReplaced() = runTest {
        dao.upsertDetails(listOf(aProductDetailsEntity(productId = 1, productName = "Pineapple")))
        dao.upsertDetails(listOf(aProductDetailsEntity(productId = 1, productName = "Apple")))

        val products = dao.observeDetails().first()

        assertEquals(listOf("Apple"), products.map { it.productName })
    }

    @Test
    fun givenExistingProductDetails_whenReplaceAll_thenUpdateAndDeleteBothOccur() = runTest {
        dao.upsertDetails(listOf(
            aProductDetailsEntity(productId = 1, productName = "Frisbee"),
            aProductDetailsEntity(productId = 2, productName = "Basketball")
        ))

        dao.replaceAllDetails(listOf(
            aProductDetailsEntity(productId = 1, productName = "Frisbee 2"),
            aProductDetailsEntity(productId = 3, productName = "Basketball")
        ))

        val products = dao.observeDetails().first().sortedBy { it.productId }

        assertEquals(listOf(1, 3), products.map { it.productId })

        assertEquals(listOf("Frisbee 2", "Basketball"), products.map { it.productName })
    }

    @Test
    fun givenNonExistentProductDetailId_whenObservedById_thenEmitsNull() = runTest {
        dao.observeDetail(100).test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun givenExistingProductDetail_whenObserved_thenEmitsThatProductDetail() = runTest {
        val product = aProductDetailsEntity(productId = 1, productName = "Product 1")
        dao.upsertDetails(listOf(product))
        val result = dao.observeDetail(1).first()
        assertEquals(product, result)
    }

    @Test
    fun givenObservedProductDetail_whenDifferentProductDetailAdded_thenOldDetailUnchanged() = runTest {
        dao.upsertDetails(listOf(aProductDetailsEntity(productId = 1, productName = "Product 1")))

        dao.observeDetail(1).test {
            val first = awaitItem()
            dao.upsertDetails(listOf(aProductDetailsEntity(productId = 2, productName = "Product 2")))
            assertEquals(first, awaitItem())
        }
    }

}