package com.example.lab10mysql_registerlogin.data.model

sealed class BuyerUiState<out T> {
    object Loading : BuyerUiState<Nothing>()
    data class Success<T>(val data: T) : BuyerUiState<T>()
    data class Error(val message: String) : BuyerUiState<Nothing>()
}