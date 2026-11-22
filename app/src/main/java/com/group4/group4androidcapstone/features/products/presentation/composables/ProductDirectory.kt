package com.group4.group4androidcapstone.features.products.presentation.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.More
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
import androidx.compose.material3.FilledIconButton
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
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.group4.group4androidcapstone.features.products.domain.Product
import com.group4.group4androidcapstone.features.products.domain.ProductCase
import com.group4.group4androidcapstone.features.products.presentation.state.ProductDirectoryState
import com.group4.group4androidcapstone.features.products.presentation.state.SortOrder
import com.group4.group4androidcapstone.ui.theme.Group4AndroidCapstoneTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDirectory(
    state: ProductDirectoryState,
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
    var categoryExpanded by remember { mutableStateOf(false) }
    var sortExpanded by remember { mutableStateOf(false) }
    var screenName by remember { mutableStateOf("") }

    val retryLabel = "Retry"
    // we're going to add a search bar (text input) to the top of the screen
    // being the first thing that can take focus, android would focus it
    // when the screen loads, we don't want that.
    // We want the user to tap on the text input when they want to search
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
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
            Column(
                modifier = Modifier.padding(vertical = 0.dp)
            ) {
                TopAppBar(
                    title = {
                        Text("${screenName} Products",
                            color = MaterialTheme.colorScheme.primary,
                            //style = MaterialTheme.typography.headlineLarge,
                            )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Go back to previous screen",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                            .padding(vertical = 8.dp)
                            .clearAndSetSemantics() {
                            contentDescription = "Search products by name with the given criteria"
                        },


                        content = {}
                    )

                    Box(
                    ) {
                        IconButton(
                            onClick = { categoryExpanded = true },
                            modifier = Modifier.semantics {
                                contentDescription = "Click to show the list of viewed product categories"
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.More,
                                contentDescription = "Product Category"
                            )
                        }
                        DropdownMenu(
                            expanded = categoryExpanded,
                            onDismissRequest = { categoryExpanded = false },
                            offset = DpOffset(x = (-120).dp, y = 0.dp)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Default") },
                                onClick = {
                                    onProductCaseChange(ProductCase.DEFAULT)
                                    onShowRecentClearChange(false)
                                    categoryExpanded = false
                                    screenName = ""
                                },
                                modifier = Modifier.semantics {
                                    contentDescription = "Show all products that meet the given search criteria"
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Recently Viewed") },
                                onClick = {
                                    onProductCaseChange(ProductCase.RECENT)
                                    onShowRecentClearChange(true)
                                    categoryExpanded = false
                                    screenName = "Recent"
                                },
                                modifier = Modifier.semantics {
                                    contentDescription = "Show recently showed products"
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Saved") },
                                onClick = {
                                    onProductCaseChange(ProductCase.SAVED)
                                    onShowRecentClearChange(false)
                                    categoryExpanded = false
                                    screenName = "Saved"
                                },
                                modifier = Modifier.semantics {
                                    contentDescription = "Show all products that have been saved with the star icon"
                                }
                            )

                        }
                    }

                    Box(
                        modifier = Modifier.padding()
                    ) {
                        IconButton(
                            onClick = { sortExpanded = true }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Sort,
                                contentDescription = "Sort currently shown products by alphabetical order or price"
                            )
                        }
                        DropdownMenu(
                            expanded = sortExpanded,
                            onDismissRequest = { sortExpanded = false },
                            offset = DpOffset(x = (-120).dp, y = 0.dp)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Name: Ascending") },
                                onClick = {
                                    onSortChanged(SortOrder.ASCENDING)
                                    sortExpanded = false
                                },
                                modifier = Modifier.semantics {
                                    contentDescription = "Sort the currently viewed products by name from A to Z"
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Name: Descending") },
                                onClick = {
                                    onSortChanged(SortOrder.DESCENDING)
                                    sortExpanded = false
                                },
                                modifier = Modifier.semantics {
                                    contentDescription = "Sort the currently viewed products by name from Z to A"
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Price") },
                                onClick = {
                                    onSortChanged(SortOrder.PRICE_LOW_TO_HIGH)
                                    sortExpanded = false
                                },
                                modifier = Modifier.semantics {
                                    contentDescription = "Sort the currently viewed products from lowest to highest price"
                                }
                            )

                        }
                    }

                }

                if (state.displayedProducts.isEmpty() && !state.isRefreshing) {
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
                    FilledIconButton(
                        onClick = {
                            onRecentProductsCleared()
                        }
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Clear recent products icon"
                        )
                    }
                }

                ExtendedFloatingActionButton(
                    text = { Text("Filters") },
                    icon = {
                        Icon(
                            Icons.Filled.Filter,
                            contentDescription = "Show additional filters"
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
                        },
                        modifier = Modifier.semantics {
                            contentDescription = "Toggle to filter by warranty"
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
                            ),
                            modifier = Modifier.semantics {
                                contentDescription = "Click to reset additional filters"
                            }
                        ) {
                            Text("Reset Filters")
                        }

                        Button(
                            onClick = {
                                onShowFilterChange(false)
                            },
                            modifier = Modifier.semantics {
                                contentDescription = "Click to close additional filters tab"
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
            onSortChanged = {}
        )
    }
}