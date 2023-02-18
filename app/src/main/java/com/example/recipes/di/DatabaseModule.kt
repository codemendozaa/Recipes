package com.example.recipes.di

import android.content.Context
import androidx.room.Room
import com.example.recipes.data.local.RecipeDatabase
import com.example.recipes.data.local.dao.RecipeDao
import com.example.recipes.data.repository.LocalDataSourceImpl
import com.example.recipes.domain.repository.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun databaseProvider(@ApplicationContext context: Context): RecipeDatabase =
        Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            RecipeDatabase.DB_NAME
        ).build()

    @Provides
    @Singleton
    fun recipeDaoProvider(recipeDatabase: RecipeDatabase): RecipeDao =
        recipeDatabase.recipeDao()

    @Provides
    @Singleton
    fun localDataSourceProvider(recipeDao: RecipeDao): LocalDataSource {
        return LocalDataSourceImpl(recipeDao = recipeDao)
    }

}