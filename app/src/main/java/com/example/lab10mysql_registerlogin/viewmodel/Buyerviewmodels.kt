package com.example.lab10mysql_registerlogin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab10mysql_registerlogin.data.api.RecycanClient
import com.example.lab10mysql_registerlogin.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ===================================================================
// ไฟล์นี้เพิ่มเข้าไปใน package viewmodel ของโปรเจกต์เพื่อน
// ===================================================================

/* -------------------- 1) CategoryVM -------------------- */
class CategoryVM : ViewModel() {

    private val _state = MutableStateFlow<BuyerUiState<List<Category>>>(BuyerUiState.Loading)
    val state: StateFlow<BuyerUiState<List<Category>>> = _state

    fun load() {
        viewModelScope.launch {
            _state.value = BuyerUiState.Loading
            try {
                val result = RecycanClient.recycanAPI.getBuyerCategories()
                _state.value = BuyerUiState.Success(result)
            } catch (e: Exception) {
                _state.value = BuyerUiState.Error(e.message ?: "โหลด category ไม่สำเร็จ")
            }
        }
    }
}

/* -------------------- 2) ListingVM -------------------- */
class ListingVM : ViewModel() {

    private val _state = MutableStateFlow<BuyerUiState<List<ListingJoin>>>(BuyerUiState.Loading)
    val state: StateFlow<BuyerUiState<List<ListingJoin>>> = _state

    fun load(category_id: Int) {
        viewModelScope.launch {
            _state.value = BuyerUiState.Loading
            try {
                val result = RecycanClient.recycanAPI.getListingByCategory(category_id)
                _state.value = BuyerUiState.Success(result)
            } catch (e: Exception) {
                _state.value = BuyerUiState.Error(e.message ?: "โหลด listing ไม่สำเร็จ")
            }
        }
    }
}

/* -------------------- 3) DetailVM -------------------- */
class DetailVM : ViewModel() {

    private val _detail = MutableStateFlow<BuyerUiState<ListingJoin>>(BuyerUiState.Loading)
    val detail: StateFlow<BuyerUiState<ListingJoin>> = _detail

    private val _buy = MutableStateFlow<BuyerUiState<TransactionCreateResponse>?>(null)
    val buy: StateFlow<BuyerUiState<TransactionCreateResponse>?> = _buy

    fun load(listing_id: Int) {
        viewModelScope.launch {
            _detail.value = BuyerUiState.Loading
            try {
                val result = RecycanClient.recycanAPI.getListingDetail(listing_id)
                _detail.value = BuyerUiState.Success(result)
            } catch (e: Exception) {
                _detail.value = BuyerUiState.Error(e.message ?: "โหลดรายละเอียดไม่สำเร็จ")
            }
        }
    }

    fun buyNow(buyer_id: Int,listing_id: Int, transaction_total: Double) {
        viewModelScope.launch {
            _buy.value = BuyerUiState.Loading
            try {
                val res = RecycanClient.recycanAPI.createTransaction(
                    TransactionRequest(
                        buyer_id = buyer_id, // TODO: เปลี่ยนเป็น user_id จาก SharedPreferences
                        listing_id = listing_id,
                        transaction_total = transaction_total
                    )
                )
                _buy.value = BuyerUiState.Success(res)
            } catch (e: Exception) {
                _buy.value = BuyerUiState.Error(e.message ?: "ยืนยันการซื้อไม่สำเร็จ")
            }
        }
    }

    fun clearBuyState() {
        _buy.value = null
    }
}