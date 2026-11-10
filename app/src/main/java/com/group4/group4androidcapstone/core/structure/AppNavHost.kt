package com.group4.group4androidcapstone.core.structure

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.group4.group4androidcapstone.core.HomeScreenRoute
import com.group4.group4androidcapstone.core.RecentOrdersRoute
import com.group4.group4androidcapstone.core.RecentProductsRoute
import com.group4.group4androidcapstone.features.HomeScreen.presentation.HomeContent
import com.group4.group4androidcapstone.features.HomeScreen.presentation.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController
    //useTwoPaneLayout:Boolean,
) {


    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute

    ) {
        composable<HomeScreenRoute> {
           HomeScreen(
//                onOrderHistory = {Unit},
//                onRecentProducts = {Unit}
           )
       }


        composable<RecentProductsRoute> {
            //ToDosScreen(useTwoPaneLayout = useTwoPaneLayout)
        }

        composable<RecentOrdersRoute> {
            //implement screen here
        }


    }

}