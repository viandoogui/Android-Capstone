package com.group4.group4androidcapstone.core.structure

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.group4.group4androidcapstone.R
import com.group4.group4androidcapstone.core.CapstoneRoute
import com.group4.group4androidcapstone.core.HomeScreenRoute
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import com.group4.group4androidcapstone.core.OrdersRoute
import com.group4.group4androidcapstone.core.ProductDirectoryRoute

enum class TopLevelDestination (
    val route: CapstoneRoute,

    @StringRes val labelRes:Int,
    val icon: ImageVector

){
    HOME(HomeScreenRoute, R.string.nav_home, Icons.Filled.Home),
    PRODUCTS(ProductDirectoryRoute, R.string.nav_ProductDir, Icons.Filled.Apps),
    ORDERS(OrdersRoute, R.string.nav_OrdersDir, Icons.Filled.ShoppingCart),
//    ORDERS(OrdersRoute, R.string.nav_orders, Icons.Filled.ShoppingCart),
//    SETTINGS(SettingsRoute, R.string.nav_settings, Icons.Filled.Settings)
}