package com.example.recipes.navigation

sealed class Screen(val route: String) {

    object Home: Screen(Screens.HOME)
    object Detail: Screen(Screens.DETAIL) {
        fun passRecipeId(recipeId: Int): String {
            return "detail_screen/$recipeId"
        }
    }
}
