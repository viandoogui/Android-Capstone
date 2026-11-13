package com.group4.group4androidcapstone.features.products.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group4.group4androidcapstone.core.LoadableState
import com.group4.group4androidcapstone.features.products.domain.GetProductDetailsUseCase
import com.group4.group4androidcapstone.features.products.domain.GetProductsUseCase
import com.group4.group4androidcapstone.features.products.domain.Product
import com.group4.group4androidcapstone.features.products.domain.ProductDetails
import com.group4.group4androidcapstone.features.products.domain.RefreshProductDetailsUseCase
import com.group4.group4androidcapstone.features.products.presentation.state.ProductDirectoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProduct: GetProductDetailsUseCase,
    private val refreshProduct: RefreshProductDetailsUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<LoadableState<ProductDetails>> = MutableStateFlow(
        LoadableState.Loading
    )

    val uiState: StateFlow<LoadableState<ProductDetails>> = _uiState.asStateFlow()

    private var loadJob: Job? = null
    private var loadedId: Int? = null

    fun loadProduct(id: Int) {
        // Don't restart (and flash loading) if this id is already being observed
        if (id == loadedId && loadJob?.isActive == true) return

        loadedId = id
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.value = LoadableState.Loading
            val hadCache = getProduct(id).first() != null

            // refresh the product data in the background
            launch {
                try {
                    refreshProduct(id)
                }
                catch (e: CancellationException) {
                    throw e
                }
                catch (e: Exception) {
                    // with a cached copy on screen, a failed refresh is silent
                    if (!hadCache) {
                        _uiState.value = LoadableState.Error(
                            e.message ?: "Couldn't load product"
                        )
                    }
                }
            }

            // establish our subscription
            getProduct(id).collect { product ->
                when {
                    product != null -> _uiState.value = LoadableState.Success(product)
                    hadCache -> _uiState.value = LoadableState.Empty // temporary state while the refresh is still underway
                    // no other useful cases - stay in loading state
                    // until the refresh either writes to the db or fails
                }
            }

        }
    }

}


