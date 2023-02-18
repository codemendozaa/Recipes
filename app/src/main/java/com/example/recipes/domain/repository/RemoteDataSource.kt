package com.example.recipes.domain.repository

import com.example.recipes.data.model.ApiResponse
import com.example.recipes.data.model.RecipeDTO

interface RemoteDataSource {
    suspend fun getAllRecipes(page: Int = 0): ApiResponse<RecipeDTO>
}