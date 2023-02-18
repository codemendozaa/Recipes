package com.example.recipes.data.repository

import com.example.recipes.data.remote.RecipesService
import com.example.recipes.data.model.ApiResponse
import com.example.recipes.data.model.RecipeDTO
import com.example.recipes.domain.repository.RemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val recipesService: RecipesService,
) : RemoteDataSource {


    override suspend fun getAllRecipes(page: Int): ApiResponse<RecipeDTO> {
        return recipesService.getAllRecipes(page)
    }

}