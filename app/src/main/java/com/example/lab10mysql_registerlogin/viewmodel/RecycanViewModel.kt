package com.example.lab10mysql_registerlogin.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab10mysql_registerlogin.data.api.RecycanClient
import com.example.lab10mysql_registerlogin.data.model.*
import com.example.recycanproject.User
import kotlinx.coroutines.launch

class RecycanViewModel : ViewModel() {
    // ================= LOGIN =================
    var loginResult by mutableStateOf<LoginResponse?>(null)
        private set
    var errorMessage by mutableStateOf("")
        private set

    val userList = mutableStateOf<List<User>>(emptyList())
    val currentUser = mutableStateOf<User?>(null)
    var isSellerMode = mutableStateOf(true)

    fun login(userEmail: String, userPassword: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(
                    user_email = userEmail,
                    user_password = userPassword
                )

                val response = RecycanClient.recycanAPI.login(request)

                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    loginResult = result
                    
                    // 🔹 เมื่อล็อกอินสำเร็จ ให้โหลดข้อมูล User ทันที
                    if (!result.error && result.user_id != null) {
                        loadUserById(result.user_id)
                    }
                } else {
                    loginResult = LoginResponse(
                        error = true,
                        message = "Invalid credentials",
                        user_id = null,
                        user_name = null
                    )
                }

            } catch (e: Exception) {
                loginResult = LoginResponse(
                    error = true,
                    message = "Network error: ${e.message}",
                    user_id = null,
                    user_name = null
                )
            }
        }
    }

    fun resetLogin() {
        loginResult = null
        errorMessage = ""
    }

    // ================= REGISTER =================
    var registerSuccess by mutableStateOf(false)
        private set

    fun register(registerData: RegisterRequest) {
        viewModelScope.launch {
            try {
                val response = RecycanClient.recycanAPI.register(registerData)

                registerSuccess = response.isSuccessful

                if (!response.isSuccessful) {
                    errorMessage = "Register Failed"
                }

            } catch (e: Exception) {
                errorMessage = "Network Error: ${e.message}"
            }
        }
    }

    fun resetRegister() {
        registerSuccess = false
    }

    fun loadUserById(id: Int) {
        viewModelScope.launch {
            try {
                val users = RecycanClient.recycanAPI.retrieveStudent()
                currentUser.value = users.find { it.user_id == id }
                Log.d("USER_LOAD", "Loaded User: ${currentUser.value?.user_name}")
            } catch (e: Exception) {
                Log.e("USER_LOAD", "Error: ${e.message}")
            }
        }
    }

    fun updateSellerProfile(
        name: String,
        email: String,
        phone: String,
        address: String
    ) {
        viewModelScope.launch {
            try {
                val user = currentUser.value ?: return@launch
                val updatedUser = user.copy(
                    user_name = name,
                    user_email = email,
                    user_phone = phone,
                    user_address = address
                )
                val response = RecycanClient.recycanAPI.updateUser(
                    user.user_id.toString(),
                    updatedUser
                )
                if (response.isSuccessful) {
                    loadUserById(user.user_id)
                }
            } catch (e: Exception) {
                println("update error: ${e.message}")
            }
        }
    }

    fun updateCustomerProfile(
        name: String,
        email: String,
        phone: String,
    ) {
        viewModelScope.launch {
            try {
                val user = currentUser.value ?: return@launch
                val updatedUser = user.copy(
                    user_name = name,
                    user_email = email,
                    user_phone = phone,
                )
                val response = RecycanClient.recycanAPI.updateUser(
                    user.user_id.toString(),
                    updatedUser
                )
                if (response.isSuccessful) {
                    loadUserById(user.user_id)
                }
            } catch (e: Exception) {
                println("update error: ${e.message}")
            }
        }
    }

    fun submitReview(request: ReviewRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = RecycanClient.recycanAPI.submitReview(request)
                if (response.isSuccessful) onSuccess()
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            }
        }
    }

    var reviewList by mutableStateOf<List<ReviewResponse>>(emptyList())
        private set
    var avgRating by mutableStateOf(0.0)
        private set

    fun fetchSellerReviews(sellerId: Int) {
        viewModelScope.launch {
            try {
                val response = RecycanClient.recycanAPI.getSellerReviews(sellerId)
                if (response.isSuccessful && response.body() != null) {
                    reviewList = response.body()!!.data
                    avgRating = reviewList.firstOrNull()?.avg_rating ?: 0.0
                }
            } catch (e: Exception) {
                Log.e("REVIEW", "Error: ${e.message}")
            }
        }
    }

    // ================= CATEGORY =================
    var categoryList by mutableStateOf<List<Category>>(emptyList())
        private set

    fun getCategories() {
        viewModelScope.launch {
            try {
                val response = RecycanClient.recycanAPI.getCategories()
                if (response.isSuccessful && response.body() != null) {
                    categoryList = response.body()!!.data
                } else {
                    errorMessage = "Fetch failed"
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            }
        }
    }

    fun createListing(
        request: ListingRequest,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response =
                    RecycanClient.recycanAPI.createListing(request)
                onResult(response.isSuccessful)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    //================== GET SellerID ==================
    val historyListings = mutableStateListOf<HistoryListing>()
    var historySellerList = mutableStateListOf<HistorySeller>()

    fun fetchHistory(sellerId: Int) = viewModelScope.launch {
        try {
            val response = RecycanClient.recycanAPI.getHistory(sellerId)
            historySellerList.clear()
            historySellerList.addAll(response)
        } catch (e: Exception) {
            Log.e("API", "History Error: ${e.message}")
        }
    }

    var currentDetail by mutableStateOf<HistorySeller?>(null)
    fun fetchDetail(tId: Int) = viewModelScope.launch {
        try {
            currentDetail = null
            val response = RecycanClient.recycanAPI.getTransactionDetail(tId)
            currentDetail = response
        } catch (e: Exception) {
            Log.e("API", "Detail Error: ${e.message}")
        }
    }

    fun fetchListings(sellerId: Int, state: String? = null) = viewModelScope.launch {
        try {
            val response = RecycanClient.recycanAPI.getSellerListings(sellerId, state)
            if (response.isSuccessful && response.body() != null) {
                historyListings.clear()
                historyListings.addAll(response.body()!!)
            }
        } catch (e: Exception) {
            Log.e("API", "Fetch Error: ${e.message}")
        }
    }

    //============== UPDATE ==============
    fun updateItem(
        id: Int,
        request: ListingRequest,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RecycanClient.recycanAPI.updateListing(id, request)
                if (response.isSuccessful && response.body()?.error == false) {
                    onSuccess()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // ================= DELETE =================
    fun deleteItem(id: Int, onDeleted: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = RecycanClient.recycanAPI.deleteListing(id)
                if (response.isSuccessful && response.body()?.error == false) {
                    onDeleted()
                }
            } catch (e: Exception) {
            }
        }
    }
}
