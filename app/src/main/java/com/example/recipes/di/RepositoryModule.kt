package com.example.recipes.di
import androidx.paging.ExperimentalPagingApi
import com.example.recipes.data.local.RecipeDatabase
import com.example.recipes.data.repository.RecipeRepositoryImpl
import com.example.recipes.domain.repository.LocalDataSource
import com.example.recipes.domain.repository.RecipeRepository
import com.example.recipes.domain.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun recipeRepositoryProvider(
        remoteDataSource: RemoteDataSource,
        recipeDatabase: RecipeDatabase,
        localDataSource: LocalDataSource
    ): RecipeRepository {
        return RecipeRepositoryImpl(
            remoteDataSource = remoteDataSource,
            recipeDatabase = recipeDatabase,
            localDataSource = localDataSource
        )
    }
}