package com.group4.group4androidcapstone.core.structure

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.group4.group4androidcapstone.core.HomeScreenRoute
import com.group4.group4androidcapstone.core.OrdersRoute
import com.group4.group4androidcapstone.core.ProductDirectoryRoute
import com.group4.group4androidcapstone.core.RecentOrdersRoute
import com.group4.group4androidcapstone.core.RecentProductsRoute
import com.group4.group4androidcapstone.features.HomeScreen.presentation.HomeContent
import com.group4.group4androidcapstone.features.products.presentation.composables.ProductDirectory
import com.group4.group4androidcapstone.features.products.presentation.composables.ProductDirectoryRoute
import com.group4.group4androidcapstone.features.products.presentation.state.ProductDirectoryState
import com.group4.group4androidcapstone.features.orders.presentation.OrderHistoryRoute

@Composable
fun AppNavHost(
    navController: NavHostController
    //useTwoPaneLayout:Boolean,
) {


    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute

    ) {
        composable<HomeScreenRoute> {
           HomeContent(
//                onOrderHistory = {Unit},
//                onRecentProducts = {Unit}
               onNavigateToOrderHistory = {
                   navController.navigate(OrdersRoute) {
                       popUpTo(navController.graph.findStartDestination().id) {
                           saveState = true
                       }
                       launchSingleTop = true
                       restoreState = true
                   }
               },
               onNavigateToProductDirectory = {
                   navController.navigate(ProductDirectoryRoute){
                       popUpTo(navController.graph.findStartDestination().id) {
                           saveState = true
                       }
                       launchSingleTop = true
                       restoreState = true
                   }
               }
           )
       }

        composable<ProductDirectoryRoute> {
            ProductDirectoryRoute(
                onBack = {
                    navController.popBackStack()
                }
            )
        }


        composable<OrdersRoute> {
            OrderHistoryRoute()
        }


    }

}