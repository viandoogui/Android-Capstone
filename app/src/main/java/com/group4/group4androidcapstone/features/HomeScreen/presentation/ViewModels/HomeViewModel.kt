package com.group4.group4androidcapstone.features.HomeScreen.presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group4.group4androidcapstone.features.HomeScreen.presentation.state.HomeUiState
import com.group4.group4androidcapstone.features.orders.domain.GetOrdersUseCase
import com.group4.group4androidcapstone.features.products.domain.GetRecentProductsUseCase
import com.group4.group4androidcapstone.features.products.domain.RefreshProductsUseCase
import com.group4.group4androidcapstone.features.products.domain.SetProductSavedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetRecentProductsUseCase,
    private val setProductSaved: SetProductSavedUseCase,
    private val getOrders: GetOrdersUseCase,
    private val refreshProductsUseCase: RefreshProductsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadRecentProducts()
        //refresh()
        getRecentOrders()
    }

     fun loadRecentProducts() {
        viewModelScope.launch {
            useCase().collect { products ->
                _uiState.update { it.copy(recentProducts = products) }
            }
        }
    }

    fun setProductIsSaved(id: Int, isSaved: Boolean) {
        viewModelScope.launch {
            setProductSaved(id, isSaved)
        }
    }
    fun refresh() {
        if (_uiState.value.isRefreshing) return
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            try {
                refreshProductsUseCase()
                val updatedOrders = getOrders()

                _uiState.update {
                    it.copy(recentOrders = updatedOrders)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Couldn't refresh products"
                    )
                }
            }finally {
                _uiState.update { it.copy(isRefreshing = false) }
            }

        }
    }

    fun getRecentOrders(){
        viewModelScope.launch {
            val orders = getOrders()
            _uiState.update { it.copy(recentOrders = orders) }
        }

    }


}