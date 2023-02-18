package com.example.recipes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipes.data.model.RecipeDTO

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: String,
    val name: String,
    val description: String,

){
    fun toRecipeDTO(): RecipeDTO =
        RecipeDTO(
            id,
            image,
            name,
            description
        )
}
