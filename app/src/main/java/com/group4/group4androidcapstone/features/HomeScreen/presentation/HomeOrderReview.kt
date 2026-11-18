package com.group4.group4androidcapstone.features.HomeScreen.presentation

import android.graphics.Color.GRAY
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.group4.group4androidcapstone.features.orders.domain.Order
import com.group4.group4androidcapstone.features.orders.presentation.OrderCard
import com.group4.group4androidcapstone.features.products.presentation.composables.ProductCard
import com.group4.group4androidcapstone.ui.theme.LocalSpacing
import com.group4.group4androidcapstone.ui.theme.Spacing


@Composable
fun HomeOrderReview(
    recentOrders: List<Order>,
    onOrderSelected: (Order) -> Unit,
    isLoading:Boolean
){



    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .size(width = 800.dp, height = 600.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        if(isLoading){
            CircularProgressIndicator()
        } else if(recentOrders.isEmpty()){
            Text("No recent orders")
        }else {
            Text("Five Most Recent Orders: ")

            Column(
                modifier = Modifier
                    .padding(LocalSpacing.current.medium)
                    .verticalScroll(state = rememberScrollState())
            ) {
                recentOrders.take(5).forEach { order ->
                    OrderCard(
                        order = order,
                        onClick = { onOrderSelected(order)}
                    )
                }
            }
        }





    }

}