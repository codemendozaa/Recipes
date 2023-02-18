package com.example.recipes.presentation.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipes.data.model.RecipeDTO
import com.example.recipes.di.IoDispatcher
import com.example.recipes.domain.uses_case.GetSelectedRecipeUC
import com.example.recipes.presentation.screens.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSelectedRecipeUC: GetSelectedRecipeUC,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _recipeSelected: MutableStateFlow<RecipeDTO?> = MutableStateFlow(null)
    val recipeSelected: StateFlow<RecipeDTO?> = _recipeSelected



    fun getSelectedRecipe(recipeId: Int) {
        viewModelScope.launch(dispatcher) {
            _recipeSelected.value =
                getSelectedRecipeUC(recipeId = recipeId)?.toRecipeDTO()
        }
    }


}