package com.example.lab10mysql_registerlogin.data.model

data class UserResponse(
    val error: Boolean?,
    val message: String?,
    val insertId: Int? = null
)
