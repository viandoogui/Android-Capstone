package com.group4.group4androidcapstone.features.products.presentation.composables

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.group4.group4androidcapstone.features.products.domain.ProductDetails
import com.group4.group4androidcapstone.features.products.presentation.viewmodels.ProductDirectoryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ProductDirectoryRoute(
    onBack: () -> Unit = {},
    viewModel: ProductDirectoryViewModel = hiltViewModel()
) {
    // get an observable copy of the state inside our viewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.loadProducts()
    }

    // navigator remembers which pane is showing and which product is in the detail pane
    // it also handles system back gesture
    val navigator = rememberListDetailPaneScaffoldNavigator<Int>()
    val scope = rememberCoroutineScope()

    SharedTransitionLayout {
        CompositionLocalProvider(LocalSharedTransitionScope provides this) {
            NavigableListDetailPaneScaffold(
                navigator = navigator,
                listPane = {
                    AnimatedPane {
                        CompositionLocalProvider(LocalPaneAnimatedScope provides this) {
                            ProductDirectory(
                                state = uiState,
                                useTwoPaneLayout = false,
                                onBack = onBack,
                                onProductSelected = { product ->
                                    scope.launch {
                                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail,
                                            product.productId)
                                    }
                                },
                                onRefresh = viewModel::loadProducts,
                                onDismissError = viewModel::dismissError,
                                onSearchQueryChange = viewModel::updateSearchQuery,
                                onSummaryChange = viewModel::updateSummaryFilter,
                            )
                        }
                    }
                },
                detailPane = {
                    AnimatedPane {
                        CompositionLocalProvider(LocalPaneAnimatedScope provides this) {
                            val productId = navigator.currentDestination?.contentKey
                            if (productId != null) {
                                ProductDetails()
                            }
                        }
                    }
                }
            )
        }
    }

}