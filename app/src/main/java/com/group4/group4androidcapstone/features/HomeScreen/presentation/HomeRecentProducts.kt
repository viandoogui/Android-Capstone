package com.group4.group4androidcapstone.features.HomeScreen.presentation

import android.graphics.Color.GRAY
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.group4.group4androidcapstone.features.HomeScreen.presentation.ViewModels.HomeViewModel
import com.group4.group4androidcapstone.features.HomeScreen.presentation.state.HomeUiState
import com.group4.group4androidcapstone.features.products.domain.Product
import com.group4.group4androidcapstone.features.products.presentation.composables.ProductCard
import com.group4.group4androidcapstone.features.products.presentation.composables.ProductList
import com.group4.group4androidcapstone.ui.theme.LocalSpacing

@Composable
fun HomeRecentProducts(
    products: List<Product>,
    onProductSelected: (Product) -> Unit,
    onProductSaved: (Int, Boolean) -> Unit,
    isLoading: Boolean,
    error: String? = null

){
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .size(width = 800.dp, height = 600.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
    if(isLoading){
        CircularProgressIndicator()
    }else if (error != null){
        Text("There was an error: (${error})")
    }else {
        Text("Recently Viewed Products:", Modifier.padding(LocalSpacing.current.small))
                Column(

                    modifier = Modifier
                        .padding(LocalSpacing.current.medium)
                        .verticalScroll(state = rememberScrollState())
                ) {
                    products.forEach { product ->
                        ProductCard(
                            product = product,
                            onClick = { onProductSelected(product) },
                            onProductSavedSet = onProductSaved
                        )
                    }
                }
            }



    }
}