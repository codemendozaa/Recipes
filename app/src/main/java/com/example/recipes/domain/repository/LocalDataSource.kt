package com.example.recipes.domain.repository

import androidx.paging.PagingSource
import com.example.recipes.domain.model.Recipe

import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertAll(recipe: List<Recipe>)
    fun getAllRecipes(): PagingSource<Int, Recipe>
    suspend fun getSelectedRecipe(recipeId: Int): Recipe?
    fun searchRecipes(text: String): Flow<List<Recipe>>

}