package com.example.recipes.data.remote

import com.example.recipes.data.model.ApiResponse
import com.example.recipes.data.model.RecipeDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesService {

    @GET("recipies")
    suspend fun getAllRecipes(
        @Query("page") page: Int = 0
    ): ApiResponse<RecipeDTO>

}