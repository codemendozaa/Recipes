package com.example.recipes.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.bikcode.rickandmortycompose.presentation.screens.home.HomeContent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    navHostController: NavHostController
) {
    Scaffold(
        topBar = {
            HomeTopBar()
        },
        content = {
            HomeContent(
                navHostController = navHostController
            )
        }
    )
}
