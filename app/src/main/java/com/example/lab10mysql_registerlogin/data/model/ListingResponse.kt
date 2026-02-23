package com.example.lab10mysql_registerlogin.data.model

data class ListingResponse(
    val error: Boolean,
    val message: String,
    val listing_id: Int?
)
