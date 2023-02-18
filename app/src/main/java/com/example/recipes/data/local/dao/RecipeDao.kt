package com.example.recipes.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipes.domain.model.Recipe

import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<Recipe>)

    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): PagingSource<Int, Recipe>

    @Query("DELETE FROM recipe")
    suspend fun clear(): Unit

    @Query("SELECT * FROM recipe WHERE id =:recipeId")
    fun getSelectedRecipes(recipeId: Int): Recipe?

    @Query("SELECT * FROM recipe WHERE name LIKE :text")
    fun searchRecipes(text: String): Flow<List<Recipe>>


}