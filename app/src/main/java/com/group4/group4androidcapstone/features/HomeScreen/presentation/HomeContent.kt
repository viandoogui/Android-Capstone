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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.group4.group4androidcapstone.ui.theme.LocalSpacing

@Composable
fun HomeContent(
    onOrderHistory: () -> Unit,
    onRecentProducts: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalSpacing.current.small),
            verticalArrangement = Arrangement.Top,
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
        }



        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HomeOrderReview()
            Button(
                onClick = { Unit },
                modifier = Modifier
                    .padding(16.dp),

                ) {
                Text("To Order History")
            }

            Spacer(modifier = Modifier.height(24.dp))
            HomeRecentProducts()
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