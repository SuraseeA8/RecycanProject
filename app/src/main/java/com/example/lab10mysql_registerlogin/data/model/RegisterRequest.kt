package com.example.lab10mysql_registerlogin.data.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("user_name") val user_name: String,
    @SerializedName("user_email") val user_email: String,
    @SerializedName("user_password") val user_password: String,
    @SerializedName("user_phone") val user_phone: String
)