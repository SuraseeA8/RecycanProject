package com.example.lab10mysql_registerlogin.data.model

data class TransactionRequest(
    val buyer_id: Int,
    val listing_id: Int,
    val transaction_total: Double
)