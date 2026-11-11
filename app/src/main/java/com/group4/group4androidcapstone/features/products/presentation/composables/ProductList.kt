package com.group4.group4androidcapstone.features.products.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.group4.group4androidcapstone.features.products.domain.Product
import com.group4.group4androidcapstone.ui.theme.LocalSpacing
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.group4.group4androidcapstone.features.products.data.ProductDto
import com.group4.group4androidcapstone.features.products.data.ProductMapper
import com.group4.group4androidcapstone.ui.theme.Group4AndroidCapstoneTheme
import kotlin.io.encoding.Base64


@Composable
fun ProductList(
    products: List<Product>,
    onProductSelected: (Product) -> Unit,
    innerPadding: PaddingValues
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val showScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium)
        ) {
            items(
                items = products,
                key = { product -> product.productId }
            ) { product ->
                ProductCard(
                    product = product,
                    onClick = {
                        onProductSelected(product)
                    }
                )
            }
        }


        AnimatedVisibility(
            visible = showScrollToTop,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(LocalSpacing.current.large)
        ) {
            SmallFloatingActionButton(
                onClick = { scope.launch { listState.animateScrollToItem(0) } }
            ) {
                Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Scroll to top")
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun ProductListPreview() {

    val products = listOf(
        Product(
            productId = 1,
            name = "Product 1",
            productNumber = "21432152",
            summary = "Summary of Product 1",
            thumbnailPhoto = byteArrayOf(),
            thumbnailPhotoFileName = "product1.jpg",
            warranty = "1 year",
            color = "Red",
            listPrice = 9.99
        ),
        Product(
            productId = 2,
            name = "Product 2",
            productNumber = "32123145",
            summary = "Summary of Product 2",
            thumbnailPhoto = byteArrayOf(),
            thumbnailPhotoFileName = "product2.jpg",
            warranty = null,
            color = "Blue",
            listPrice = 4.99
        ),
        Product(
            productId = 3,
            name = "Product 3",
            productNumber = "56465138543",
            summary = "Summary of Product 3",
            thumbnailPhoto = byteArrayOf(),
            thumbnailPhotoFileName = "product3.jpg",
            warranty = null,
            color = "Green",
            listPrice = 19.99
        ),
    )
    Group4AndroidCapstoneTheme {
        ProductList(products = products, onProductSelected = {}, innerPadding = PaddingValues())
    }
}