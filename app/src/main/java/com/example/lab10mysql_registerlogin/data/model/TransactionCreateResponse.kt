package com.example.lab10mysql_registerlogin.data.model

data class TransactionCreateResponse(
    val message: String,
    val transaction_id: Int? = null,
    val listing_id: Int? = null
)