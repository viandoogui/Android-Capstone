package com.group4.group4androidcapstone.features.HomeScreen.presentation.ViewModels

import app.cash.turbine.test
import com.group4.group4androidcapstone.features.orders.domain.GetOrdersUseCase
import com.group4.group4androidcapstone.features.orders.domain.Order
import com.group4.group4androidcapstone.features.orders.domain.OrderRepository
import com.group4.group4androidcapstone.features.products.domain.GetRecentProductsUseCase
import com.group4.group4androidcapstone.features.products.domain.Product
import com.group4.group4androidcapstone.features.products.domain.ProductRepository
import com.group4.group4androidcapstone.features.products.domain.RefreshProductsUseCase
import com.group4.group4androidcapstone.features.products.domain.SetProductSavedUseCase
import com.group4.group4androidcapstone.features.products.domain.SetProductTimeUseCase
import com.group4.group4androidcapstone.testutil.MainDispatcherRule
import com.group4.group4androidcapstone.testutil.builders.aProduct
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime
import kotlin.Int

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val testProduct = aProduct(
        productId = 101,
        name = "Mountain Bike",
        productNumber = "testProductNumber",
        summary = "summary",
        thumbnailPhoto = byteArrayOf(),
        thumbnailPhotoFileName = "thumbnailPhotoFileName",
        warranty = null,
        color = null,
        listPrice= 3.99,
        stockLevel= 0,
        viewedTime = null,
        isSaved = false,
        savedTime = null
    )
    val testOrder = Order(
        id = 101, customerId = 1, firstName = "Brandy", lastName = "Chandra",
        orderDate = LocalDateTime.now(), shipDate = LocalDateTime.now(),
        status = 1, statusName = "Pending", items = emptyList()
    )





    @Test
    fun homeViewModel_whenInitialized_thenShowLoading() = runTest {
        //create a test product repo
        val productRepository = object : ProductRepository {
            override fun observeRecentProducts(): Flow<List<Product>> = flowOf(listOf(testProduct))
            override fun observeProducts(): Flow<List<Product>> = flowOf(emptyList())
            override fun observeSavedProducts(): Flow<List<Product>> = flowOf(emptyList())
            override fun observeProductDetails(id: Int) = flowOf(null)
            override suspend fun refreshProducts() {}
            override suspend fun refreshProductDetails(id: Int) {}
            override suspend fun setProductViewedTime(id: Int, viewedTime: LocalDateTime?) {}
            override suspend fun clearProductViewedTimes() {}
            override suspend fun setProductSaved(id: Int, isSaved: Boolean) {}
        }

        //create a test order repo
        val orderRepository = object : OrderRepository {
            override suspend fun getOrders(): List<Order> = listOf(testOrder)
        }

        //make a viewmodel with the test repos
        val viewModel = HomeViewModel(
            useCase = GetRecentProductsUseCase(productRepository),
            setProductSaved = SetProductSavedUseCase(productRepository),
            getOrders = GetOrdersUseCase(orderRepository),
            refreshProductsUseCase = RefreshProductsUseCase(productRepository),
            setProductTime = SetProductTimeUseCase(productRepository)
        )

        //capture the ui state
        //test for loading state
        viewModel.uiState.test {
            var state = awaitItem()
            while (state.recentProducts.isEmpty() || state.recentOrders.isEmpty()) {
                state = awaitItem()
            }

            //ensure the data is correct and loading is finished
            assertEquals("Mountain Bike", state.recentProducts.first().name)
            assertEquals(101, state.recentOrders.first().id)
            assertFalse(state.isProductRefreshing)
            assertFalse(state.isOrderRefreshing)
        }

    }



    @Test
    fun refreshProducts_triggersRefreshUseCase() = runTest {
        //flow:
        // test -> repo -> viewmodel -> repo -> test

        var refreshUseCaseWasCalled = false //track if refresh is called


            //setup a test product repo
        val productRepository = object : ProductRepository {
            override fun observeRecentProducts(): Flow<List<Product>> = flowOf(listOf(testProduct))
            override fun observeProducts(): Flow<List<Product>> = flowOf(emptyList())
            override fun observeSavedProducts(): Flow<List<Product>> = flowOf(emptyList())
            override fun observeProductDetails(id: Int) = flowOf(null)
            override suspend fun refreshProducts() {
                refreshUseCaseWasCalled = true //make sure refresh is called
            }
            override suspend fun refreshProductDetails(id: Int) {}
            override suspend fun setProductViewedTime(id: Int, viewedTime: LocalDateTime?) {}
            override suspend fun clearProductViewedTimes() {}
            override suspend fun setProductSaved(id: Int, isSaved: Boolean) {}
        }

        //init the view model
        val viewModel = HomeViewModel(
            useCase = GetRecentProductsUseCase(productRepository),
            setProductSaved = SetProductSavedUseCase(productRepository),
            getOrders = GetOrdersUseCase(object : OrderRepository { override suspend fun getOrders() = listOf(testOrder) }),
            refreshProductsUseCase = RefreshProductsUseCase(productRepository),
            setProductTime = SetProductTimeUseCase(productRepository)
        )

        // trigger the refresh
        viewModel.refreshProducts()

        //needed to complete the async refresh before assertion
        testScheduler.advanceUntilIdle()

        assertTrue(refreshUseCaseWasCalled)
    }


}