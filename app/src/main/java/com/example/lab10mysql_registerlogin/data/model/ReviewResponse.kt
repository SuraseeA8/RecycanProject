package com.example.lab10mysql_registerlogin.data.model

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("review_id") val review_id: Int,
    @SerializedName("rating") val rating: Int,
    @SerializedName("comment") val comment: String?,
    @SerializedName("reviewer_name") val reviewer_name: String,
    @SerializedName("avg_rating") val avg_rating: Double?
)