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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.group4.group4androidcapstone.features.HomeScreen.presentation.ViewModels.HomeViewModel
import com.group4.group4androidcapstone.features.products.domain.Product
import com.group4.group4androidcapstone.ui.theme.LocalSpacing
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun HomeContent(
    //onOrderHistory: () -> Unit,
    //onRecentProducts: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    onProductSelected: (Product) -> Unit,
    onNavigateToOrderHistory: () -> Unit,
    onNavigateToProductDirectory: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
            .verticalScroll(state = rememberScrollState()),

    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalSpacing.current.large),
           verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome Back, Brandy Chandra!",
                style = MaterialTheme.typography.headlineMedium,
                modifier= Modifier
                    .width(800.dp)
                    .padding(LocalSpacing.current.small)
                    .background(MaterialTheme.colorScheme.surface)
            )

            Spacer(modifier = Modifier.height(24.dp))

            HomeOrderReview()
            Button(
                onClick = onNavigateToOrderHistory,
                modifier = Modifier
                    .padding(16.dp),

                ) {
                Text("To Order History")
            }

            Spacer(modifier = Modifier.height(24.dp))

            HomeRecentProducts(products = state.recentProducts, onProductSelected = onProductSelected)
            Button(
                onClick = { Unit },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text("To Recent Products")
            }


        }

    }
}