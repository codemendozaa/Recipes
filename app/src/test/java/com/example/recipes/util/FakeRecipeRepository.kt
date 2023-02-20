package com.example.recipes.util

import androidx.paging.PagingData
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRecipeRepository : RecipeRepository {

    private var error = false

    fun shouldReturnError(error: Boolean) {
        this.error = error
    }

    override fun getAllRecipes(): Flow<PagingData<Recipe>> {
        return flowOf(PagingData.from(listOf(recipe)))
    }

    override suspend fun getSelectedRecipes(recipesId: Int): Recipe? {
        return if(error) null else recipe
    }

    override fun searchRecipes(text: String): Flow<List<Recipe>> {
        return flowOf(listOf(recipe, recipe.copy(name = "test")))
    }
}