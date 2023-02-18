package com.example.recipes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipes.data.local.dao.DatabaseConverter
import com.example.recipes.data.local.dao.RecipeDao
import com.example.recipes.data.local.dao.RemoteKeysDao
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.model.RemoteKeys


@Database(entities = [Recipe::class, RemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverter::class)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        const val DB_NAME = "recipe.db"
    }
}