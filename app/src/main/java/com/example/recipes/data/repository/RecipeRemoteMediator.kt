package com.example.recipes.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.recipes.data.local.RecipeDatabase
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.model.RemoteKeys
import com.example.recipes.domain.repository.RemoteDataSource
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
@OptIn(ExperimentalPagingApi::class)
class RecipeRemoteMediator(
    private val recipeDatabase: RecipeDatabase,
    private val remoteDataSource: RemoteDataSource
) : RemoteMediator<Int, Recipe>() {

    private val recipeDao = recipeDatabase.recipeDao()
    private val remoteKeysDao = recipeDatabase.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Recipe>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: RECIPE_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeysForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {
            val response = remoteDataSource.getAllRecipes(page = page)

            val recipes = response.results.map { it.toRecipeDomain() }
            val endOfPagination = recipes.isEmpty()

            recipeDatabase.withTransaction {
                // Initial load of data
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    recipeDao.clear()
                }

                val prevKey = if (page == RECIPE_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPagination) null else page + 1
                val keys = recipes.map {
                    RemoteKeys(
                        recipeId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                remoteKeysDao.insertAll(keys)
                recipeDao.insertAll(recipes)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, Recipe>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { recipes ->
                remoteKeysDao.remoteKeysRecipeId(recipes.id)
            }
    }

    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, Recipe>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { recipe ->
                // Get the remote keys of the first items retrieved
                remoteKeysDao.remoteKeysRecipeId(recipe.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Recipe>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                remoteKeysDao.remoteKeysRecipeId(repoId)
            }
        }
    }

    companion object {
        private const val RECIPE_STARTING_PAGE_INDEX = 1
    }
}