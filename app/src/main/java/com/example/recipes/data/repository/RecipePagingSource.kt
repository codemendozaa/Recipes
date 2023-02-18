package com.example.recipes.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.repository.RemoteDataSource
import javax.inject.Inject
import java.io.IOException
import retrofit2.HttpException

private const val RECIPE_STARTING_PAGE_INDEX = 1

class RecipePagingSource @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, Recipe>() {

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition = anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition = anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val position = params.key ?: RECIPE_STARTING_PAGE_INDEX
        return try {
            val response = remoteDataSource.getAllRecipes(page = position)
            val recipes = response.results.map { it.toRecipeDomain() }
            val nextKey = if (response.info.next.isNullOrBlank()) {
                null
            } else {
                position + 1
            }

            LoadResult.Page(
                data = recipes,
                prevKey = if (response.info.prev.isNullOrBlank()) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}