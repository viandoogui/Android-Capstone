package com.group4.group4androidcapstone.features.HomeScreen.presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group4.group4androidcapstone.features.HomeScreen.presentation.state.HomeUiState
import com.group4.group4androidcapstone.features.orders.domain.GetOrdersUseCase
import com.group4.group4androidcapstone.features.products.domain.GetRecentProductsUseCase
import com.group4.group4androidcapstone.features.products.presentation.state.ProductDirectoryState
import com.group4.group4androidcapstone.features.products.presentation.viewmodels.ProductDirectoryViewModel
import com.group4.group4androidcapstone.features.products.presentation.viewmodels.ProductDirectoryViewModel.Companion.SEARCH_KEY
import com.group4.group4androidcapstone.features.products.presentation.viewmodels.ProductDirectoryViewModel.Companion.SUMMARY_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetRecentProductsUseCase

) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    // expose (publicly) a read-only view of the state object
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadRecentProducts()
    }

    private fun loadRecentProducts() {
        viewModelScope.launch {
            useCase().collect { products ->
                _uiState.update { it.copy(recentProducts = products) }
            }
        }
    }


}