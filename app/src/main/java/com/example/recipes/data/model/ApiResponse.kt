package com.example.recipes.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
@SerializedName("info")
val info: InfoDTO,
@SerializedName("results")
val results: List<T>
)