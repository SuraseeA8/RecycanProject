package com.example.lab10mysql_registerlogin.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab10mysql_registerlogin.data.api.RecycanClient
import com.example.lab10mysql_registerlogin.data.model.*
import kotlinx.coroutines.launch

class RecycanViewModel : ViewModel() {
    // ================= LOGIN =================
    var loginResult by mutableStateOf<LoginResponse?>(null)
        private set
    var errorMessage by mutableStateOf("")
        private set

    fun login(userEmail: String, userPassword: String) {
        viewModelScope.launch {
            try {

                val request = LoginRequest(
                    user_email = userEmail,
                    user_password = userPassword
                )

                val response = RecycanClient.recycanAPI.login(request)

                if (response.isSuccessful && response.body() != null) {
                    loginResult = response.body()
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

    fun fetchListings(sellerId: Int) = viewModelScope.launch {
        try { val response = RecycanClient.recycanAPI.getSellerListings(sellerId)
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
