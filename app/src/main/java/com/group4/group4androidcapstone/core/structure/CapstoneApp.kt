package com.group4.group4androidcapstone.core.structure

import androidx.compose.runtime.Composable

import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2


@Composable
fun CapstoneApp(
) {

   // val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
//    val useTwoPaneLayout = windowSizeClass.isWidthAtLeastBreakpoint(
//        WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND
//    )

    val navController = rememberNavController()


    AppNavigationShell(navController = navController) {
        AppNavHost(
            navController = navController,
        )
    }
}



