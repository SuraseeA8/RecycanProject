package com.example.lab10mysql_registerlogin.data.model

data class CategoryListResponse(
    val error: Boolean,
    val data: List<Category>
)