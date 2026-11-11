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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DockedSearchBar
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.group4.group4androidcapstone.core.presentation.TextFilter
import com.group4.group4androidcapstone.features.products.domain.Product
import com.group4.group4androidcapstone.features.products.presentation.state.ProductDirectoryState

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
    onGenreChange: (String) -> Unit = {},
    onLocationChange: (String) -> Unit = {},
    onShowFilterChange: (Boolean) -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val retryLabel = "Retry"
    // we're going to add a search bar (text input) to the top of the screen
    // being the first thing that can take focus, android would focus it
    // when the screen loads, we don't want that.
    // We want the user to tap on the text input when they want to search
    val focusManager = LocalFocusManager.current
    LaunchedEffect(Unit) {
        // delay until the next rendering frame executes
        withFrameNanos {}
        focusManager.clearFocus(force = true)
    }

    // a failed refresh should keep the cached product on screen
    // and tell hte user why with a way to try again
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
                Row(){
                    Button (
                        onClick = {}
                    ){
                        Text("Recently Viewed")
                    }
                    Button (
                        onClick = {}
                    ){
                        Text("Saved Products")
                    }
                }


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
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    content = {}
                )

                Text("Displayed Products")

            }
        },

        floatingActionButton = {
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
                }
            )
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
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                ProductList(
                    products = state.displayedProducts,
                    onProductSelected = onProductSelected,
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
                    TextFilter(
                        label = "Summary Filter",
                        filter = state.summaryFilter,
                        onFilterChange = onLocationChange
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                onGenreChange("")
                                onLocationChange("")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        ) {
                            Text("Clear")
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
    ProductDirectory(
        state = ProductDirectoryState(),
        useTwoPaneLayout = false,
    )
}