package com.example.lab10mysql_registerlogin.data.model

data class Category(
    val category_id: Int,
    val category_name: String,
    val description: String?,
    val price_per_kg: Double,
    val image_url: String?
)