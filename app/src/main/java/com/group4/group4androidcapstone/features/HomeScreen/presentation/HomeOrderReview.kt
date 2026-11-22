package com.group4.group4androidcapstone.features.HomeScreen.presentation

import android.graphics.Color.GRAY
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.group4.group4androidcapstone.features.HomeScreen.presentation.ViewModels.HomeViewModel
import com.group4.group4androidcapstone.features.orders.domain.Order
import com.group4.group4androidcapstone.features.orders.presentation.OrderCard
import com.group4.group4androidcapstone.features.products.presentation.composables.ProductCard
import com.group4.group4androidcapstone.ui.theme.LocalSpacing
import com.group4.group4androidcapstone.ui.theme.Spacing


@Composable
fun HomeOrderReview(
    recentOrders: List<Order>,
    onOrderSelected: (Order) -> Unit,
    isLoading:Boolean,
    error:String? = null,
    viewModel: HomeViewModel
){


    Column(

        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .width(1000.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        if(isLoading){
            CircularProgressIndicator()
        }else if(error != null){
            Text("There was an error: (${error})")
        }
        else {

            Text("Five Most Recent Orders: ", Modifier.padding(LocalSpacing.current.small))

            Row(

                modifier = Modifier
                    .padding(LocalSpacing.current.small)
                    .horizontalScroll(state = rememberScrollState())
            ) {
                recentOrders.take(5).forEach { order ->
                    OrderCard(
                        order = order,
                        onClick = { onOrderSelected(order)}
                    )
                }


            }
            Button(modifier = Modifier.align(Alignment.End)
                .padding(LocalSpacing.current.small)
                .semantics {
                    contentDescription = "Refresh five most recent orders"
                },
                onClick = { viewModel.getRecentOrders() }){
                Text("Refresh")
            }
        }





    }

}