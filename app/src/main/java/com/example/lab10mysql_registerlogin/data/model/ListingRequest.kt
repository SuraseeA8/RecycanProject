package com.example.lab10mysql_registerlogin.data.model

data class ListingRequest(
    val weight: Double,
    val price: Double,
    val place: String,
    val phone: String,
    val sell_time: String,
    val seller_id: Int,
    val category_id: Int
)