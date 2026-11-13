package com.group4.group4androidcapstone.features.products.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.group4.group4androidcapstone.core.room.AppDatabase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import junit.framework.TestCase.assertEquals
import org.junit.Test

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
    fun givenEmptyTable_whenObserved_thenEmptyListEmitted() = runTest {
        dao.observeProducts().test {
            assertEquals(emptyList<ProductEntity>(), awaitItem())
        }
    }
}