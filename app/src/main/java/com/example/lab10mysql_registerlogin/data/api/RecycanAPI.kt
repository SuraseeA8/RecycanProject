package com.example.lab10mysql_registerlogin.data.api

import com.example.lab10mysql_registerlogin.data.model.*
import retrofit2.Response
import retrofit2.http.*
import android.util.Log
import com.example.recycanproject.User

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

    @GET(value = "alluser")
    suspend fun retrieveStudent(): List<User>

    @FormUrlEncoded
    @POST(value = "insertuser")
    suspend fun insertUser(
        @Field("user_name") user_name: String,
        @Field("user_email") user_email: String,
        @Field("user_password") user_password: String,
        @Field("user_phone") user_phone: String,
        @Field("user_address") user_address: String,
        @Field("user_image") user_image: String

    ): UserResponse

    @PUT(value = "updateuser/{id}")
    suspend fun updateUser(
        @Path(value = "id") id: String,
        @Body user: User
    ): retrofit2.Response<UserResponse>

    // ================= CATEGORY =================
    @GET("AllCategories")
    suspend fun getCategories(): Response<CategoryListResponse>

    // ================= LISTING =================
    @GET("listings/all/{seller_id}")
    suspend fun getSellerListings(
        @Path("seller_id") sellerId: Int,
        @Query("state") state: String? = null
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

    @GET("transaction/history/{seller_id}")
    suspend fun getHistory(@Path("seller_id") id: Int): List<HistorySeller>

    @GET("transaction/detail/{transaction_id}")
    suspend fun getTransactionDetail(
        @Path("transaction_id") id: Int): HistorySeller

}
