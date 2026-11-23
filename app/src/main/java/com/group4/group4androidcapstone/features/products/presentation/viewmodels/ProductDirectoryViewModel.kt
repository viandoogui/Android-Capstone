package com.group4.group4androidcapstone.features.products.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group4.group4androidcapstone.features.products.domain.ClearProductTimesUseCase
import com.group4.group4androidcapstone.features.products.domain.GetProductsUseCase
import com.group4.group4androidcapstone.features.products.domain.Product
import com.group4.group4androidcapstone.features.products.domain.GetRecentProductsUseCase
import com.group4.group4androidcapstone.features.products.domain.GetSavedProductsUseCase
import com.group4.group4androidcapstone.features.products.domain.ProductCase
import com.group4.group4androidcapstone.features.products.domain.RefreshProductsUseCase
import com.group4.group4androidcapstone.features.products.domain.SetProductSavedUseCase
import com.group4.group4androidcapstone.features.products.presentation.state.ProductDirectoryState
import com.group4.group4androidcapstone.features.products.presentation.state.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ProductDirectoryViewModel @Inject constructor(
    private val getAvailableProducts: GetProductsUseCase,
    private val getRecentProducts: GetRecentProductsUseCase,
    private val getSavedProducts: GetSavedProductsUseCase,
    private val refreshProducts: RefreshProductsUseCase,
    private val setProductSaved: SetProductSavedUseCase,
    private val clearRecentlyViewedProducts: ClearProductTimesUseCase,
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()
) : ViewModel() {

    // keep the actual mutable state value private
    private val _uiState = MutableStateFlow(
        ProductDirectoryState(
            searchQuery = savedStateHandle[SEARCH_KEY] ?: "",
        )
    )

    // expose (publicly) a read-only view of the state object
    val uiState: StateFlow<ProductDirectoryState> = _uiState.asStateFlow()

    private var observeJob: Job? = null

    init {
        // establish a subscription to the observable collection
        // of products from our Room db
        viewModelScope.launch {
            // by subscribing (collecting) inside the viewModelScope
            // the subscription will be automatically released and disposed
            // when the viewModel is destroyed (i.e. when the user
            // navigates away from this screen)
            setProductUseCase(ProductCase.DEFAULT)
        }
    }

    fun updateShowClear(shouldShow: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                showClearRecentProducts = shouldShow
            )
        }
    }

    fun clearRecentProducts() {
        viewModelScope.launch {
            clearRecentlyViewedProducts()
        }
    }

    fun setProductIsSaved(id: Int, isSaved: Boolean) {
        viewModelScope.launch {
            setProductSaved(id, isSaved)
        }
    }

    fun setProductUseCase(case: ProductCase) {
        observeJob?.cancel()

        observeJob = viewModelScope.launch {
            val currentFlow = when (case) {
                ProductCase.DEFAULT -> getAvailableProducts()
                ProductCase.RECENT -> getRecentProducts()
                ProductCase.SAVED -> getSavedProducts()
            }

            currentFlow.collect { products ->
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
                savedStateHandle[SEARCH_KEY] = ""
                _uiState.update { it.copy(isRefreshing = false, searchQuery = "") }


            }
            // NEVER swallow a CancellationException
            // doing so can orphan the coroutine (suspendable)
            catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
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
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun updateWarrantyFilter(displayWarrantyOnly: Boolean) {

        _uiState.update {
            it.copy(displayWarrantyOnly = displayWarrantyOnly)
        }
    }

    fun onSortChanged(newSort: SortOrder) {
        _uiState.update { currentState ->
            val sortedList = sortProducts(currentState.products, newSort)
            currentState.copy(
                products = sortedList,
                currentSort = newSort
            )
        }
    }

    fun sortProducts(list: List<Product>, sortOrder: SortOrder): List<Product> {
        return when (sortOrder) {
            SortOrder.ASCENDING -> list.sortedBy { it.name }
            SortOrder.DESCENDING -> list.sortedByDescending { it.name }
            SortOrder.PRICE_LOW_TO_HIGH -> list.sortedBy { it.listPrice }
        }
    }

    fun getProductsBasedOnWarranty(list: List<Product>, displayWarrantyOnly: Boolean): List<Product> {
        return if (displayWarrantyOnly) {
            list.filter { !it.warranty.isNullOrEmpty() }
        } else {
            list
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
    }


}