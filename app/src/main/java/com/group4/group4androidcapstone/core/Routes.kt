package com.group4.group4androidcapstone.core

import kotlinx.serialization.Serializable


sealed interface CapstoneRoute

@Serializable
data object HomeScreenRoute: CapstoneRoute{

}

@Serializable

data object RecentOrdersRoute: CapstoneRoute{

}

@Serializable
data object RecentProductsRoute: CapstoneRoute{

}




@Serializable
data class ProductDirectoryRoute(val selectedProductId: Int? = null): CapstoneRoute{

}

@Serializable
data object OrdersRoute: CapstoneRoute{

}