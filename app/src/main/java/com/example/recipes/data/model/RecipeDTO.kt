package com.example.recipes.data.model

import com.example.recipes.domain.model.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String
){
    fun toRecipeDomain(): Recipe =
        Recipe(
            id,
            image,
            name,
            description
        )
}