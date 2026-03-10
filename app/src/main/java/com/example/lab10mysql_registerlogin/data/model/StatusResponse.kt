package com.example.lab10mysql_registerlogin.data.model

data class Status(
    val transaction_id: Int,
    val transaction_state: String,
    val pickup_place: String,
    val seller_name: String,
    val seller_phone: String,
    val category_name: String
)

data class StatusResponse(
    val error: Boolean,
    val data: Status
)

data class StatusUpdateRequest(
    val transaction_state: String,
    val pickup_state: String
)