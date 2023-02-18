package com.example.recipes.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.recipes.data.model.RecipeDTO
import com.example.recipes.domain.repository.RecipeRepository
import com.example.recipes.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _searchedRecipes = MutableStateFlow<List<RecipeDTO>>(emptyList())
    val searchedRecipes = _searchedRecipes.asStateFlow()


    private var searchJob: Job? = null

    val getAllRecipes =
        recipeRepository.getAllRecipes().cachedIn(viewModelScope).map {
            it.map { recipe -> recipe.toRecipeDTO() }
        }



    fun searchRecipes(text: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            viewModelScope.launch(dispatcher) {
                recipeRepository.searchRecipes(text = text)
                    .map { it.map { recipe -> recipe.toRecipeDTO() } }
                    .collect {
                        _searchedRecipes.value = it
                    }
            }
        }
    }
}