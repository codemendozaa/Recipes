package com.example.recipes.data.repository

import androidx.paging.PagingSource
import com.example.recipes.data.local.dao.RecipeDao
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(private val recipeDao: RecipeDao) : LocalDataSource {

    override suspend fun insertAll(recipe: List<Recipe>) {
        recipeDao.insertAll(recipe)
    }

    override fun getAllRecipes(): PagingSource<Int, Recipe> {
        return recipeDao.getAllRecipes()
    }

    override suspend fun getSelectedRecipe(recipeId: Int): Recipe? {
        return recipeDao.getSelectedRecipes(recipeId = recipeId)
    }

    override fun searchRecipes(text: String): Flow<List<Recipe>> {
        return recipeDao.searchRecipes(text = text)
    }

}