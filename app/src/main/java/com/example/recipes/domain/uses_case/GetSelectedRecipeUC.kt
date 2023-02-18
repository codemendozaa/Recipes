package com.example.recipes.domain.uses_case


import com.example.recipes.domain.model.Recipe
import com.example.recipes.domain.repository.RecipeRepository
import javax.inject.Inject

class GetSelectedRecipeUC @Inject constructor(private val recipeRepository: RecipeRepository) {

    suspend operator fun invoke(recipeId: Int): Recipe? {
        return recipeRepository.getSelectedRecipes(recipesId = recipeId)
    }
}