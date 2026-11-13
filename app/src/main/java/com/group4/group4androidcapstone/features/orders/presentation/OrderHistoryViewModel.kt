package com.group4.group4androidcapstone.features.orders.presentation

import androidx.lifecycle.ViewModel
import com.group4.group4androidcapstone.features.orders.domain.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.group4.group4androidcapstone.features.orders.domain.OrderSorter

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(OrderHistoryUiState())
    val uiState: StateFlow<OrderHistoryUiState> = _uiState.asStateFlow()


    fun loadOrders() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val orders = getOrdersUseCase()

                val sortedOrders = if (_uiState.value.newestFirst) {
                    OrderSorter.newestFirst(orders)
                } else {
                    OrderSorter.oldestFirst(orders)
                }

                _uiState.value = _uiState.value.copy(
                    orders = sortedOrders,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Unable to load orders"
                )
            }
        }
    }

    fun changeSort(newestFirst: Boolean) {
        val currentState = _uiState.value

        val sortedOrders = if (newestFirst) {
            OrderSorter.newestFirst(currentState.orders)
        } else {
            OrderSorter.oldestFirst(currentState.orders)
        }

        _uiState.value = currentState.copy(
            orders = sortedOrders,
            newestFirst = newestFirst
        )
    }
}
