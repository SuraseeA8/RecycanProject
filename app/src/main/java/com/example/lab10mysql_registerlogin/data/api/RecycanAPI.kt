package com.example.lab10mysql_registerlogin.data.api

import com.example.lab10mysql_registerlogin.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface RecycanAPI {

    // ================= AUTH =================

    @POST("register")
    suspend fun register(
        @Body body: RegisterRequest
    ): Response<RegisterResponse>

    @POST("login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<LoginResponse>

    // ================= CATEGORY =================

    @GET("AllCategories")
    suspend fun getCategories(): Response<CategoryListResponse>

    // ================= LISTING =================

    @POST("listing/create")
    suspend fun createListing(
        @Body request: ListingRequest
    ): Response<Map<String, Any>>

}
