package com.group4.group4androidcapstone.features.HomeScreen.presentation

import android.graphics.Color.GRAY
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HomeRecentProducts(){



    Column(
        modifier = Modifier
            .background(Color(GRAY)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ){
        Text("Home screen recent products displayed here")

    }
}