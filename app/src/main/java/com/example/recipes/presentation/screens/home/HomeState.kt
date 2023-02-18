package com.example.recipes.presentation.screens.home

import com.example.recipes.data.model.RecipeDTO

data class HomeState(
    val recipes: List<RecipeDTO> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
