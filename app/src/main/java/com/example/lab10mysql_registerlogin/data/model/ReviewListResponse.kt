package com.example.lab10mysql_registerlogin.data.model

import com.google.gson.annotations.SerializedName

data class ReviewListResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("data") val data: List<ReviewResponse>
)