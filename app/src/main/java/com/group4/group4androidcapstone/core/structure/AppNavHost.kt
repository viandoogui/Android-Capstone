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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.material3.Text
import com.group4.group4androidcapstone.features.orders.presentation.OrderHistoryScreen
import com.group4.group4androidcapstone.features.orders.presentation.OrderHistoryViewModel

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
                   navController.navigate(OrdersRoute()) {
                       popUpTo(navController.graph.findStartDestination().id) {
                           saveState = true
                       }
                       launchSingleTop = true
                       restoreState = true
                   }
               },
               onNavigateToProductDirectory = {
                   navController.navigate(ProductDirectoryRoute()){
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
               },
               onOrderSelected = { selectedOrder ->
                   navController.navigate(OrdersRoute(selectedOrderId = selectedOrder.id)) {
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



        composable<OrdersRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<OrdersRoute>()

            val viewModel: OrderHistoryViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsState().value

            val selectedOrderId = rememberSaveable {
                mutableStateOf(route.selectedOrderId)
            }

            LaunchedEffect(Unit) {
                viewModel.loadOrders()
            }

            val selectedOrder = uiState.orders.firstOrNull {
                it.id == selectedOrderId.value
            }

            BackHandler(enabled = selectedOrderId.value != null) {
                selectedOrderId.value = null
            }

            when {
                selectedOrderId.value == null -> {
                    OrderHistoryScreen(
                        uiState = uiState,
                        onSortChange = { newestFirst ->
                            viewModel.changeSort(newestFirst)
                        },
                        onOrderSelected = { order ->
                            selectedOrderId.value = order.id
                        }
                    )
                }

                selectedOrder != null -> {
                    OrderDetailsScreen(
                        order = selectedOrder,
                        onBack = {
                            selectedOrderId.value = null
                        }
                    )
                }

                uiState.isLoading -> {
                    Text("Loading order...")
                }

                uiState.errorMessage != null -> {
                    Text(uiState.errorMessage)
                }

                else -> {
                    Text("Order not found")
                }
            }
        }
    }
}