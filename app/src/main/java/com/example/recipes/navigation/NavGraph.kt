package com.bikcode.rickandmortycompose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.recipes.navigation.Screen
import com.example.recipes.presentation.screens.detail.DetailScreen
import com.example.recipes.presentation.screens.home.HomeScreen
import com.example.recipes.presentation.screens.util.Constants.DETAILS_RECIPE_KEY
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalFoundationApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioMediumBouncy)
    val tweenSpec = tween<IntOffset>(durationMillis = 2000, easing = CubicBezierEasing(0.08f,0.93f,0.68f,1.27f))
    AnimatedNavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(route = Screen.Home.route) {
            HomeScreen(navHostController = navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(DETAILS_RECIPE_KEY) {
                type = NavType.IntType
            }),
            enterTransition = {
                when(initialState.destination.route) {
                    "home_screen" ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tweenSpec)
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "home_screen" -> {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                    }
                    else -> null
                }
            }
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt(DETAILS_RECIPE_KEY)
            DetailScreen(navHostController = navController, recipeId = recipeId ?: -1)
        }
    }
}