package com.example.recipes.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.recipes.data.local.RecipeDatabase
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.repository.LocalDataSource
import com.example.recipes.domain.repository.RecipeRepository
import com.example.recipes.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@ExperimentalPagingApi
class RecipeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val recipeDatabase: RecipeDatabase,
    private val localDataSource: LocalDataSource
) : RecipeRepository {

    override fun getAllRecipes(): Flow<PagingData<Recipe>> {

        val recipeSourceFactory = { localDataSource.getAllRecipes() }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = RecipeRemoteMediator(
                recipeDatabase = recipeDatabase,
                remoteDataSource = remoteDataSource
            ),
            pagingSourceFactory = recipeSourceFactory
        ).flow
    }

    override suspend fun getSelectedRecipes(recipesId: Int): Recipe? {
        return localDataSource.getSelectedRecipe(recipeId = recipesId)
    }


    override fun searchRecipes(text: String): Flow<List<Recipe>> {
        return localDataSource.searchRecipes(text = "%$text%")
    }



    companion object {
        const val NETWORK_PAGE_SIZE = 1
    }

}