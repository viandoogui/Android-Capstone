package com.group4.group4androidcapstone.features.orders.domain

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class GetOrdersUseCaseTest {

    @Test
    fun invoke_fetchesOrdersFromRepositoryAndSortsByDate() = runTest {
        //test data
        val olderOrder = Order(id = 1, customerId = 1, firstName = "A", lastName = "B", orderDate = LocalDateTime.of(2026, 1, 1, 0, 0), shipDate = LocalDateTime.now(), status = 1, statusName = "Pending", items = emptyList())
        val newerOrder = Order(id = 2, customerId = 1, firstName = "A", lastName = "B", orderDate = LocalDateTime.of(2026, 2, 1, 0, 0), shipDate = LocalDateTime.now(), status = 1, statusName = "Pending", items = emptyList())

        //ensure the repository is actually called
        //create a test repo that returns the test data
        var repositoryWasCalled = false
        val fakeRepository = object : OrderRepository {
            override suspend fun getOrders(): List<Order> {
                repositoryWasCalled = true
                return listOf(olderOrder, newerOrder)
            }
        }

        //pass in fake repo and get its data
        val useCase = GetOrdersUseCase(fakeRepository)
        val result = useCase()

        //assert the results are right
        assert(repositoryWasCalled)
        assertEquals(2, result.first().id)
        assertEquals(1, result.last().id)
    }
}