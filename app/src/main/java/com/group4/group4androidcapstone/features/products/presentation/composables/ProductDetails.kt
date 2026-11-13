package com.group4.group4androidcapstone.features.products.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.group4.group4androidcapstone.core.LoadableState
import com.group4.group4androidcapstone.features.products.domain.ProductDetails
import com.group4.group4androidcapstone.features.products.presentation.viewmodels.ProductDetailsViewModel
import com.group4.group4androidcapstone.ui.theme.Group4AndroidCapstoneTheme
import com.group4.group4androidcapstone.ui.theme.LocalSpacing
import kotlin.io.encoding.Base64

@Composable
fun ProductDetails(
    productId: Int,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    when (state) {
        is LoadableState.Loading -> {
            CircularProgressIndicator()
        }

        is LoadableState.Empty -> {
            Text("Product not found")
        }

        is LoadableState.Success -> {
            val product = (state as LoadableState.Success<ProductDetails>).data

            Surface(
                shape = RoundedCornerShape(LocalSpacing.current.medium),
                color = MaterialTheme.colorScheme.surfaceVariant,
                ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.large),
                    modifier = Modifier
                        .padding(LocalSpacing.current.large)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    ProductDetailText("Id", product.productId)
                    ProductDetailText("Product Name", product.productName)
                    ProductDetailText("Product Number", product.productNumber)
                    ProductDetailText("Summary", product.summary)
                    AsyncImage(
                        model = product.photo,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(96.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                    ProductDetailText("Photo File Name", product.photoFileName)
                    ProductDetailText("Color", product.color)
                    ProductDetailText("List Price", product.listPrice)
                    ProductDetailText("Stock Level", product.stockLevel)
                    ProductDetailText("Product Line", product.productLine)
                    ProductDetailText("Product Model Id", product.productModelId)
                    ProductDetailText("Product Model Name", product.productModelName)
                    ProductDetailText("Wheel Description", product.wheelDescription)
                    ProductDetailText("Saddle Description", product.saddleDescription)
                    ProductDetailText("Pedal Description", product.pedalDescription)
                    ProductDetailText("Rider Experience", product.riderExperience)
                    ProductDetailText("Manufacturer", product.manufacturer)
                    ProductDetailText("Bike Frame", product.bikeFrame)
                    ProductDetailText("Crankset", product.crankset)
                    ProductDetailText("Material", product.material)
                    ProductDetailText("Style", product.style)
                    ProductDetailText("Warranty Period", product.warrantyPeriod)
                    ProductDetailText("Warranty Description", product.warrantyDescription)
                    ProductDetailText("Maintenance Description", product.maintenanceDescription)
                    ProductDetailText("Number Of Steps", product.numberOfSteps)
                    ProductDetailText("Setup Hours", product.setupHours)
                    ProductDetailText("Machine Hours", product.machineHours)
                    ProductDetailText("Labor Hours", product.laborHours)
                    ProductDetailText("Lot Size", product.lotSize)


                }
            }
        }

        is LoadableState.Error -> {
            val message = (state as LoadableState.Error).message
            Text(
                "Error: $message",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}