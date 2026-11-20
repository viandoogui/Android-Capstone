package com.group4.group4androidcapstone.features.HomeScreen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.group4.group4androidcapstone.features.HomeScreen.presentation.ViewModels.HomeViewModel
import com.group4.group4androidcapstone.features.orders.domain.Order
import com.group4.group4androidcapstone.features.products.domain.Product
import com.group4.group4androidcapstone.ui.theme.LocalSpacing
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime

@Composable
fun HomeContent(
    viewModel: HomeViewModel = hiltViewModel(),
    onProductSelected: (Product) -> Unit,
    onNavigateToOrderHistory: () -> Unit,
    onNavigateToProductDirectory: () -> Unit,
    onOrderSelected:(Order)->Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.loadRecentProducts()
    }

    Column(
//        isRefreshing = state.isRefreshing,
//        onRefresh = viewModel::refresh,
        modifier = Modifier
            .fillMaxSize()
            //.padding(16.dp)
            .statusBarsPadding()
            .verticalScroll(state = rememberScrollState()),

    ) {
        if(state.error != null){
         Text(state.error ?:  "there was an error")
        }



            Column(
                modifier = Modifier
                    .fillMaxSize(),
                   // .padding(LocalSpacing.current.large),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier
                    .padding(LocalSpacing.current.medium)
                    //.width(800.dp)
                    .background(MaterialTheme.colorScheme.surface),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Text(
                    "Welcome Back, Brandy Chandra!",
                    modifier = Modifier.padding(LocalSpacing.current.medium),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                }

                Spacer(modifier = Modifier.height(24.dp))

                HomeOrderReview(
                    recentOrders = state.recentOrders,
                    isLoading = state.isOrderRefreshing,
                    onOrderSelected = onOrderSelected,
                    error = state.error,
                    viewModel = viewModel
                )

                Button(
                    onClick = onNavigateToOrderHistory,
                    modifier = Modifier
                        .padding(LocalSpacing.current.medium),

                    ) {
                    Text("To Order History")
                }

                Spacer(modifier = Modifier.height(24.dp))

                HomeRecentProducts(
                    products = state.recentProducts,
                    onProductSelected = { product ->
                        viewModel.setProductViewedTime(product.productId, LocalDateTime.now())
                        onProductSelected(product)},
                    onProductSaved = { id, isSaved ->
                        viewModel.setProductIsSaved(id, isSaved)
                    },
                    isLoading = state.isProductRefreshing,
                    error = state.error,
                    viewModel = viewModel
                    )
                Button(
                    onClick = onNavigateToProductDirectory,
                    modifier = Modifier
                        .padding(LocalSpacing.current.medium)
                ) {
                    Text("To Products")
                }


            }


    }
}