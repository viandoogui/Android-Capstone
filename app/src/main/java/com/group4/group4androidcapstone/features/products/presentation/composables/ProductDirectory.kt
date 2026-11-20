package com.group4.group4androidcapstone.features.products.presentation.composables

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.group4.group4androidcapstone.features.products.domain.Product
import com.group4.group4androidcapstone.features.products.domain.ProductCase
import com.group4.group4androidcapstone.features.products.presentation.state.ProductDirectoryState
import com.group4.group4androidcapstone.features.products.presentation.state.SortOrder
import com.group4.group4androidcapstone.ui.theme.Group4AndroidCapstoneTheme
import com.group4.group4androidcapstone.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDirectory(
    state: ProductDirectoryState,
    useTwoPaneLayout: Boolean,
    onBack: () -> Unit = {},
    onProductSelected: (Product) -> Unit = {},
    onRefresh: () -> Unit = {},
    onDismissError: () -> Unit = {},
    onSearchQueryChange: (String) -> Unit = {},
    onWarrantyToggled: (Boolean) -> Unit = {},
    onShowFilterChange: (Boolean) -> Unit = {},
    onSortChanged: (SortOrder) -> Unit,
    onProductCaseChange: (ProductCase) -> Unit = {},
    onProductSavedSet: (Int, Boolean) -> Unit = { a, b -> },
    onRecentProductsCleared: () -> Unit = {},
    onShowRecentClearChange: (Boolean) -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var expanded by remember { mutableStateOf(false) }

    val retryLabel = "Retry"
    // we're going to add a search bar (text input) to the top of the screen
    // being the first thing that can take focus, android would focus it
    // when the screen loads, we don't want that.
    // We want the user to tap on the text input when they want to search
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current // <-- Get keyboard controller
    LaunchedEffect(Unit) {
        // delay until the next rendering frame executes
        withFrameNanos {}
        focusManager.clearFocus(force = true)
        keyboardController?.hide()
    }

    // a failed refresh should keep the cached product on screen
    // and tell the user why with a way to try again
    LaunchedEffect(state.errorMessage) {
        val message = state.errorMessage ?: return@LaunchedEffect
        val result = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = retryLabel,
            duration = SnackbarDuration.Long
        )
        onDismissError()
        if (result == SnackbarResult.ActionPerformed) {
            onRefresh()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text("Products")
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back arrow"
                            )
                        }
                    }
                )
                Row() {
                    Button(
                        onClick = {
                            onProductCaseChange(ProductCase.DEFAULT)
                            onShowRecentClearChange(false)
                        }
                    ) {
                        Text("Default")
                    }
                    Button(
                        onClick = {
                            onProductCaseChange(ProductCase.RECENT)
                            onShowRecentClearChange(true)
                        }
                    ) {
                        Text("Recently Viewed")
                    }
                    Button(
                        onClick = {
                            onProductCaseChange(ProductCase.SAVED)
                            onShowRecentClearChange(false)
                        }
                    ) {
                        Text("Saved Products")
                    }
                }
                Row() {
                    DockedSearchBar(
                        inputField = {
                            SearchBarDefaults.InputField(
                                query = state.searchQuery,
                                onQueryChange = onSearchQueryChange,
                                onSearch = {},
                                expanded = false,
                                onExpandedChange = {},
                                placeholder = { Text("Search") },
                                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) }
                            )
                        },
                        expanded = false,
                        onExpandedChange = {},
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 4.dp),
                        content = {}
                    )

                    IconButton(
                        onClick = { expanded = true }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Sort,
                            contentDescription = "Sort"
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Name: Ascending") },
                            onClick = {
                                onSortChanged(SortOrder.ASCENDING)
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Name: Descending") },
                            onClick = {
                                onSortChanged(SortOrder.DESCENDING)
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Price") },
                            onClick = {
                                onSortChanged(SortOrder.PRICE_LOW_TO_HIGH)
                                expanded = false
                            }
                        )

                    }

                }

                Text("Displayed Products")
                if (state.displayedProducts.isEmpty()) {
                    Text("No products found")
                }

            }
        },

        floatingActionButton = {

            Column(
                modifier = Modifier.padding(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                if(state.showClearRecentProducts) {
                    ExtendedFloatingActionButton(
                        text = { Text("Clear Recent") },
                        icon = {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Clear recent products icon"
                            )
                        },
                        onClick = {
                            onRecentProductsCleared()
                        },
                        modifier = Modifier.height(45.dp)
                    )
                }

                ExtendedFloatingActionButton(
                    text = { Text("Filters") },
                    icon = {
                        Icon(
                            Icons.Filled.Filter,
                            contentDescription = "Filter icon"
                        )
                    },
                    onClick = {
                        onShowFilterChange(true)
                    },
                    modifier = Modifier.height(45.dp)
                )
            }

        }

    ) { innerPadding ->

        if (useTwoPaneLayout) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PullToRefreshBox(
                    isRefreshing = state.isRefreshing,
                    onRefresh = onRefresh,
                    modifier = Modifier.weight(1f)
                ) {
                    ProductList(
                        products = state.displayedProducts,
                        onProductSelected = onProductSelected,
                        onProductSavedSet = onProductSavedSet,
                        innerPadding = PaddingValues(0.dp)
                    )
                }
                VerticalDivider()
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Selected Products")
                    val selectedProduct = state.selectedProduct
                    AnimatedVisibility(
                        visible = selectedProduct != null,
                        enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
                        exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top)
                    ) {
                        if (selectedProduct != null) {
                            // TODO, DISPLAY PRODUCT DETAILS HERE
                        }
                    }

                }
            }

        } else {
            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = onRefresh,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                ProductList(
                    products = state.displayedProducts,
                    onProductSelected = onProductSelected,
                    onProductSavedSet = onProductSavedSet,
                    innerPadding = PaddingValues(0.dp)
                )
            }
        }


        if (state.showFilter) {
            ModalBottomSheet(
                onDismissRequest = {
                    onShowFilterChange(false)
                }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Filter",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text("Filter By Warranty")
                    Switch(
                        checked = state.displayWarrantyOnly,
                        onCheckedChange = {
                            onWarrantyToggled(it)
                        }
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                onWarrantyToggled(false)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        ) {
                            Text("Reset Filters")
                        }

                        Button(
                            onClick = {
                                onShowFilterChange(false)
                            }
                        ) {
                            Text("Close")
                        }
                    }
                }
            }
        }

    }

}


@Preview(
    name = "Compact View",
    widthDp = 390,
    heightDp = 844
)
@Composable
fun CompactProductDirectoryPreview() {
    Group4AndroidCapstoneTheme() {
        ProductDirectory(
            state = ProductDirectoryState(),
            useTwoPaneLayout = false,
            onSortChanged = {}
        )
    }
}