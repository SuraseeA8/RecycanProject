package com.example.lab10mysql_registerlogin.data.model

// --- Purchase Detail ---
data class BuyerDetail(
    val transaction_id: Int,
    val category_name: String,
    val weight: Double,
    val seller: String,
    val place: String,
    val transaction_total: Double,
    val image_url: String
)

data class BuyerDetailResponse(
    val error: Boolean,
    val data: BuyerDetail
)

// --- WasteList ---
data class BuyerListingModel(
    val listing_id: Int,
    val weight: Double,
    val price: Double,
    val place: String,
    val phone: String,
    val listing_state: String,
    val category_name: String,
    val transaction_state: String? = null
)

data class BuyerListingResponse(
    val error: Boolean,
    val data: List<BuyerListingModel>
)

// --- Status ---
data class BuyerStatusData(
    val transaction_id: Int,
    val transaction_state: String,
    val pickup_place: String,
    val seller_name: String,
    val seller_phone: String,
    val category_name: String
)

data class BuyerStatusResponse(
    val error: Boolean,
    val data: BuyerStatusData
)

data class BuyerStatusUpdateRequest(
    val transaction_id: Int,
    val transaction_state: String
)