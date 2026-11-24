package com.group4.group4androidcapstone.features.HomeScreen.presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group4.group4androidcapstone.features.HomeScreen.presentation.state.HomeUiState
import com.group4.group4androidcapstone.features.orders.domain.GetOrdersUseCase
import com.group4.group4androidcapstone.features.products.domain.GetRecentProductsUseCase
import com.group4.group4androidcapstone.features.products.domain.RefreshProductsUseCase
import com.group4.group4androidcapstone.features.products.domain.SetProductSavedUseCase
import com.group4.group4androidcapstone.features.products.domain.SetProductTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetRecentProductsUseCase,
    private val setProductSaved: SetProductSavedUseCase,
    private val getOrders: GetOrdersUseCase,
    private val refreshProductsUseCase: RefreshProductsUseCase,
    private val setProductTime: SetProductTimeUseCase
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
             _uiState.update { it.copy(isProductRefreshing = true, productError = null) }
             try {
                 useCase().collect { products ->
                     _uiState.update {
                         it.copy(recentProducts = products, isProductRefreshing = false)
                     }
                 }
             } catch (e: Exception) {
                 _uiState.update {
                     it.copy(productError = e.message ?: "Couldn't load products", isProductRefreshing = false)
                 }
             }
         }
    }

    fun setProductIsSaved(id: Int, isSaved: Boolean) {
        viewModelScope.launch {
            setProductSaved(id, isSaved)
        }
    }
    fun refreshProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isProductRefreshing = true, productError = null) }
            try {
                refreshProductsUseCase()
                val updatedProducts = useCase().first()
                _uiState.update { it.copy(recentProducts = updatedProducts) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(productError = e.message ?: "Couldn't refresh products")
                }
            } finally {
                _uiState.update { it.copy(isProductRefreshing = false) }
            }
        }
    }

    fun getRecentOrders(){
        viewModelScope.launch {
            _uiState.update { it.copy(isOrderRefreshing = true, orderError = null) }
            try {
                val orders = getOrders()
                _uiState.update {
                    it.copy(recentOrders = orders, isOrderRefreshing = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(orderError = e.message ?: "Couldn't load recent orders", isOrderRefreshing = false)
                }
            }
        }

    }

    fun setProductViewedTime(id: Int, viewedTime: LocalDateTime) {
        viewModelScope.launch {
            setProductTime(id, viewedTime)

            val updatedProducts = useCase().first()
            _uiState.update { it.copy(recentProducts = updatedProducts) }
        }
    }


}