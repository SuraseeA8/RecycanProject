package com.example.lab10mysql_registerlogin.data.model

data class HistoryListing(
    val listing_id: Int,
    val weight: Double,
    val price: Double,
    val place: String?,
    val listing_state: String?,
    val category_name: String,
    val price_per_kg: Double,
    val image_url: String? = null
)
