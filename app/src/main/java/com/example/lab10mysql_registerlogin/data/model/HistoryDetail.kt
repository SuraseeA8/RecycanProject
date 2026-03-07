package com.example.lab10mysql_registerlogin.data.model


data class HistorySold(
    val transaction_id: Int,
    val transaction_total: Double,
    val transaction_date: String?,
    val buyer_name: String?,
    val buyer_phone: String?,
    val category_name: String?,
    val weight: Double
)