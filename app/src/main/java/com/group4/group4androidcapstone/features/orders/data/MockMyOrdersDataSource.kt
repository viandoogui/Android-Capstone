package com.group4.group4androidcapstone.features.orders.data

import com.group4.group4androidcapstone.features.orders.data.MyOrderDto

class MockMyOrdersDataSource: MyOrdersDataSource {

    override suspend fun getOrders(
        customerId: Int,
        page: Int,
        pageSize: Int
    ): MyOrderResponseDto {
         return MyOrderResponseDto(
            customerId = customerId,
            page = page,
            pageSize = pageSize,
            totalOrders = 2,
            orders = listOf(
                MyOrderDto(
                    orderNumber = 1,
                    orderReference = "111",
                    orderDate = "2026-11-07T02:39:20.712Z",
                    shipDate = "2026-11-07T02:39:20.712Z",
                    status = 0,
                    statusName = "In Transit",
                    subTotal = 13.0,
                    taxAmt = 1.0,
                    freight = 3.0,
                    totalDue = 17.0,
                    lines = listOf(
                        MyOrderLineDto(
                            lineId = 1,
                            productId = 101,
                            productName = "Order Line Item 1",
                            quantity = 2,
                            unitPrice = 2.0,
                            unitPriceDiscount = 0.0,
                            lineTotal = 4.0
                        ),
                        MyOrderLineDto(
                            lineId = 2,
                            productId = 102,
                            productName = "Order Line Item 2",
                            quantity = 3,
                            unitPrice = 3.0,
                            unitPriceDiscount = 0.0,
                            lineTotal = 9.0
                        )
                    )
                ),
                MyOrderDto(
                    orderNumber = 2,
                    orderReference = "222",
                    orderDate = "2026-11-07T02:42:31.020Z",
                    shipDate = "2026-11-07T02:42:31.020Z",
                    status = 1,
                    statusName = "Delivered",
                    subTotal = 108.0,
                    taxAmt = 15.0,
                    freight = 25.0,
                    totalDue = 148.0,
                    lines = listOf(
                        MyOrderLineDto(
                            lineId = 3,
                            productId = 103,
                            productName = "Order Line Item 3",
                            quantity = 4,
                            unitPrice = 12.0,
                            unitPriceDiscount = 0.0,
                            lineTotal = 48.0
                        ),
                        MyOrderLineDto(
                            lineId = 4,
                            productId = 104,
                            productName = "Order Line Item 3",
                            quantity = 10,
                            unitPrice = 6.0,
                            unitPriceDiscount = 0.0,
                            lineTotal = 60.0
                        )
                    )
                )
            )

        )

    }

}