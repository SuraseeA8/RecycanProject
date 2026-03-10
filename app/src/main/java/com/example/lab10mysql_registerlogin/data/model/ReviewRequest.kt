package com.example.lab10mysql_registerlogin.data.model

import com.google.gson.annotations.SerializedName

data class ReviewRequest(
    @SerializedName("rating") val rating: Int,
    @SerializedName("comment") val comment: String,
    @SerializedName("reviewer_id") val reviewer_id: Int,
    @SerializedName("transaction_id") val transaction_id: Int
)