package com.group4.group4androidcapstone.core.structure

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.group4.group4androidcapstone.R
import com.group4.group4androidcapstone.core.CapstoneRoute
import com.group4.group4androidcapstone.core.HomeScreenRoute
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home

enum class TopLevelDestination (
    val route: CapstoneRoute,

    @StringRes val labelRes:Int,
    val icon: ImageVector

){
    HOME(HomeScreenRoute, R.string.nav_home, Icons.Filled.Home),
//    ARTISTS(ArtistListRoute, R.string.nav_artists, Icons.Filled.Person),
//    TODOS(ToDosRoute, R.string.nav_todos, Icons.Filled.CheckCircle),
//    ORDERS(OrdersRoute, R.string.nav_orders, Icons.Filled.ShoppingCart),
//    SETTINGS(SettingsRoute, R.string.nav_settings, Icons.Filled.Settings)
}