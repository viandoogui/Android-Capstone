package com.group4.group4androidcapstone.core.structure

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
fun AppNavigationShell(
    navController: NavHostController,
    content: @Composable () -> Unit
) {

    val customItemColors = NavigationSuiteDefaults.itemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            unselectedIconColor = MaterialTheme.colorScheme.tertiary, //Unselected icon color
            unselectedTextColor = MaterialTheme.colorScheme.tertiary, // Unselected label color
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,   // Selected icon color
            selectedTextColor = MaterialTheme.colorScheme.onPrimary     //Selected label color
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            unselectedIconColor = MaterialTheme.colorScheme.tertiary,
            unselectedTextColor = MaterialTheme.colorScheme.tertiary,
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTextColor = MaterialTheme.colorScheme.onPrimary
        )
    )


    val currentDestination = navController
        .currentBackStackEntryAsState().value ?.destination

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            TopLevelDestination.entries.forEach{destination ->
                item(
                    selected = currentDestination?.hierarchy?.any{
                        it.hasRoute(destination.route::class)
                    } == true,
                    onClick = {
                        navController.navigate(destination.route){
                            //have one backstack per tab. if you leave a tab and come back, its as you left it
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(destination.icon, contentDescription = null)},
                    label = { Text(stringResource(destination.labelRes),color = MaterialTheme.colorScheme.tertiary) },
                    colors = customItemColors,
                    modifier = Modifier.clearAndSetSemantics {
                        contentDescription = "Click to navigate to " + destination.name.lowercase() + " screen."
                    }
                )


            }
        },
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = MaterialTheme.colorScheme.surface,
            navigationBarContentColor = MaterialTheme.colorScheme.tertiary,
            navigationRailContainerColor = MaterialTheme.colorScheme.surface,
            navigationRailContentColor = MaterialTheme.colorScheme.onSurface,

        )
    ){
        //the nav bar handles its system insets
        //but we need to provide space for them and side margins
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 16.dp)
        ){
            content()

        }

    }


}