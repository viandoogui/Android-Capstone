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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.group4.group4androidcapstone.features.products.domain.Product
import com.group4.group4androidcapstone.features.products.presentation.composables.ProductCard
import com.group4.group4androidcapstone.features.products.presentation.composables.ProductList
import com.group4.group4androidcapstone.ui.theme.LocalSpacing

@Composable
fun HomeRecentProducts(){



    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .size(width = 800.dp, height = 600.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ){
//        Text("Home screen recent products displayed here",
//            modifier= Modifier.padding(LocalSpacing.current.large))

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
                    productNumber = "21432152",
                    summary = "Summary of Product 2",
                    thumbnailPhoto = byteArrayOf(),
                    thumbnailPhotoFileName = "product2.jpg",
                    warranty = "2 year",
                    color = "blue",
                    listPrice = 10.99
                )
        )

        ProductList(
            products = products,
            onProductSelected = {Unit},
            innerPadding = PaddingValues()
        )


    }
}