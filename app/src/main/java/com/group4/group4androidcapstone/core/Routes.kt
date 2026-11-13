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
data object ProductDirectoryRoute: CapstoneRoute{

}

@Serializable
data object OrdersRoute: CapstoneRoute{

}