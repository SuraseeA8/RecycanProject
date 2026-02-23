package com.example.lab10mysql_registerlogin.data.model

data class Listing(
    val listing_id: Int,
    val weight: Double,
    val price: Double,
    val place: String?,
    val listing_state: String?,
    val seller_id: Int,
    val category_id: Int,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?
)
