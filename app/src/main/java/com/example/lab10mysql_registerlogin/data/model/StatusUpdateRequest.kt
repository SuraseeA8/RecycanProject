package com.example.lab10mysql_registerlogin.data.model

data class StatusUpdateRequestWithId(
    val transaction_id: Int,
    val transaction_state: String
)