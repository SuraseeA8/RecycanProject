package com.example.lab10mysql_registerlogin.data.api

import com.example.lab10mysql_registerlogin.data.model.*
import retrofit2.Response
import retrofit2.http.*
import android.util.Log

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

    @GET("listings/all/{seller_id}")
    suspend fun getSellerListings(
        @Path("seller_id") sellerId: Int
    ): Response<List<HistoryListing>>

    @POST("listing/create")
    suspend fun createListing(
        @Body request: ListingRequest
    ): Response<Map<String, Any>>

    @DELETE("listing/delete/{id}")
    suspend fun deleteListing(
        @Path("id") id: Int
    ): Response<ListingResponse>

    @PUT("listing/update/{id}")
    suspend fun updateListing(
        @Path("id") id: Int,
        @Body request: ListingRequest
    ): Response<ListingResponse>

}
