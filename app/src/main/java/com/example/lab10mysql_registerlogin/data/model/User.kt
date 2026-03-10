package com.example.recycanproject

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName(  "user_id") val user_id: Int = 0,
    @SerializedName(  "user_name") val user_name: String = "",
    @SerializedName(  "user_email") val user_email: String = "",
    @SerializedName(  "user_password") val user_password: String = "",
    @SerializedName(  "user_phone") val user_phone: String = "",
    @SerializedName("user_address") val user_address: String = "",

)
