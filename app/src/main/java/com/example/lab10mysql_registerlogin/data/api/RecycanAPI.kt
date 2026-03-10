package com.example.lab10mysql_registerlogin.data.api

import com.example.lab10mysql_registerlogin.data.model.*
import com.example.lab10mysql_registerlogin.data.model.LoginRequest
import com.example.lab10mysql_registerlogin.data.model.LoginResponse
import com.example.lab10mysql_registerlogin.data.model.RegisterRequest
import com.example.lab10mysql_registerlogin.data.model.RegisterResponse
import com.example.recycanproject.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RecycanAPI {

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

    @POST("review")
    suspend fun submitReview(
        @Body request: ReviewRequest
    ): Response<Map<String, Any>>

    @GET("review/seller/{seller_id}")
    suspend fun getSellerReviews(
        @Path("seller_id") sellerId: Int
    ): Response<ReviewListResponse>

    @PUT(value = "updateuser/{id}")
    suspend fun updateUser(
        @Path(value = "id") id: String,
        @Body user: User
    ): Response<UserResponse>

    // ================= CATEGORY =================
    @GET("AllCategories")
    suspend fun getCategories(): Response<CategoryListResponse>

    // ================= LISTING (Seller) =================
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

    // ================= SELLER HISTORY =================
    @GET("transaction/history/{seller_id}")
    suspend fun getHistory(@Path("seller_id") id: Int): List<HistorySeller>

    @GET("transaction/detail/{transaction_id}")
    suspend fun getTransactionDetail(
        @Path("transaction_id") id: Int
    ): HistorySeller

    // ================= BUYER - Category & Listing =================
    @GET("category")
    suspend fun getBuyerCategories(): List<Category>

    @GET("listing")
    suspend fun getListingByCategory(
        @Query("category_id") category_id: Int
    ): List<ListingJoin>

    @GET("listing/{listing_id}")
    suspend fun getListingDetail(
        @Path("listing_id") listing_id: Int
    ): ListingJoin

    @POST("transaction")
    suspend fun createTransaction(
        @Body request: TransactionRequest
    ): TransactionCreateResponse

    // ================= BUYER - Purchase Detail =================
    @GET("buyer/history/detail/{transaction_id}")
    fun getBuyerHistoryDetail(
        @Path("transaction_id") id: Int
    ): Call<BuyerDetailResponse>

    // ================= BUYER - Status Update =================
    @GET("buyer/listings")
    fun getBuyerListings(): Call<BuyerListingResponse>

    @GET("buyer/status/{listing_id}")
    fun getBuyerStatus(
        @Path("listing_id") id: Int
    ): Call<BuyerStatusResponse>

    @POST("buyer/updateStatus")
    fun updateBuyerStatus(
        @Body status: BuyerStatusUpdateRequest
    ): Call<BuyerStatusResponse>
}