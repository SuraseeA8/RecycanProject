package com.example.lab10mysql_registerlogin.data.model
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("user_id") val user_id: Int?,
    @SerializedName("user_name") val user_name: String?
)
