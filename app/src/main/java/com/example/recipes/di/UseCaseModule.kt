package com.example.recipes.di


import com.example.recipes.domain.repository.RecipeRepository
import com.example.recipes.domain.uses_case.GetSelectedRecipeUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun getSelectedRecipeUseCaseProvider(recipeRepository: RecipeRepository): GetSelectedRecipeUC =
        GetSelectedRecipeUC(recipeRepository = recipeRepository)
}