package com.example.lab10mysql_registerlogin.data.model

data class ListingJoin(
    val listing_id: Int,
    val weight: Double,
    val price: Double,
    val place: String,
    val listing_state: String,
    val seller_id: Int,
    val category_id: Int,
    val createdAt: String?,
    val updatedAt: String?,
    val deletedAt: String?,
    val category_name: String,
    val description: String?,
    val price_per_kg: String,
    val image_url: String?,
    val user_id: Int,
    val user_name: String,
    val user_email: String?,
    val user_phone: String?
)