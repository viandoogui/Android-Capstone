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
import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.toRoute
import com.group4.group4androidcapstone.features.orders.domain.Order
import com.group4.group4androidcapstone.features.orders.presentation.OrderDetailsScreen
import com.group4.group4androidcapstone.features.products.presentation.composables.ProductDetails
import kotlinx.coroutines.launch
import java.time.LocalDateTime

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
               },
               onProductSelected = { selectedProduct ->
                   navController.navigate(ProductDirectoryRoute(selectedProductId = selectedProduct.productId)) {
                       popUpTo(navController.graph.findStartDestination().id) {
                           saveState = true
                       }
                       launchSingleTop = true
                       restoreState = false
                   }
               }

           )
       }

        composable<ProductDirectoryRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<ProductDirectoryRoute>()
            ProductDirectoryRoute(
                selectedProductId =route.selectedProductId ,
                onBack = {
                    navController.popBackStack()
                }
            )
        }


        composable<OrdersRoute> {
            val selectedOrder = remember {
                mutableStateOf<Order?>(null)
            }

            BackHandler(enabled = selectedOrder.value != null) {
                selectedOrder.value = null
            }

            val order = selectedOrder.value

            if (order == null) {
                OrderHistoryRoute(
                    onOrderSelected = { selected ->
                        selectedOrder.value = selected
                    }
                )
            } else {
                OrderDetailsScreen(
                    order = order,
                    onBack = {
                        selectedOrder.value = null
                    }
                )
            }
        }

    }

}