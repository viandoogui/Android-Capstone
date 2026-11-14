package com.group4.group4androidcapstone.features.products.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group4.group4androidcapstone.features.products.domain.GetProductsUseCase
import com.group4.group4androidcapstone.features.products.domain.RefreshProductsUseCase
import com.group4.group4androidcapstone.features.products.presentation.state.ProductDirectoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDirectoryViewModel @Inject constructor(
    private val getAvailableProducts: GetProductsUseCase,
    private val refreshProducts: RefreshProductsUseCase,
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()
): ViewModel() {

    // keep the actual mutable state value private
    private val _uiState = MutableStateFlow(
        ProductDirectoryState(
            searchQuery = savedStateHandle[SEARCH_KEY] ?: "",
            summaryFilter = savedStateHandle[SUMMARY_KEY] ?: "",
        )
    )

    // expose (publicly) a read-only view of the state object
    val uiState: StateFlow<ProductDirectoryState> = _uiState.asStateFlow()

    init {
        // establish a subscription to the observable collection
        // of products from our Room db
        viewModelScope.launch {
            // by subscribing (collecting) inside the viewModelScope
            // the subscription will be automatically released and disposed
            // when the viewModel is destroyed (i.e. when the user
            // navigates away from this screen)
            getAvailableProducts().collect { products ->
                _uiState.update { currentState ->
                    currentState.copy(
                        products = products
                    )
                }
            }
        }
    }

    // loadProducts does not get the data for the view,
    // instead it refreshes the local Room db
    fun loadProducts() {
        // call an async data loading method and we are not in an async method
        if (_uiState.value.isRefreshing) return

        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isRefreshing = true,
                    errorMessage = null
                )
            }

            try {
                refreshProducts()
                _uiState.update { it.copy(isRefreshing = false) }
            }
            // NEVER swallow a CancellationException
            // doing so can orphan the coroutine (suspendable)
            catch (e: CancellationException) {
                throw e
            }
            catch (e: Exception) {
                // cached products are already on screen, they can stay there
                // we just need to surface the error
                _uiState.update {
                    it.copy(
                        isRefreshing = false,
                        errorMessage = e.message ?: "Couldn't refresh products"
                    )
                }
            }

        }
    }

    fun dismissError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun updateSearchQuery(query: String) {
        savedStateHandle[SEARCH_KEY] = query
        _uiState.update { it.copy(searchQuery = query)}
    }

    fun updateSummaryFilter(summary: String) {
        savedStateHandle[SUMMARY_KEY] = summary
        _uiState.update { currentState ->
            currentState.copy(
                summaryFilter = summary
            )
        }
    }


    fun updateShowFilter(show: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                showFilter = show
            )
        }
    }

    fun setSelectedProduct(productId: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedProductId = productId
            )
        }
    }

    companion object {
        const val SEARCH_KEY = "searchFilter"
        const val SUMMARY_KEY = "summaryFilter"
    }

}