package com.example.recipes.domain.repository

import androidx.paging.PagingData
import com.example.recipes.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllRecipes(): Flow<PagingData<Recipe>>
    suspend fun getSelectedRecipes(recipesId: Int): Recipe?
    fun searchRecipes(text: String): Flow<List<Recipe>>

}