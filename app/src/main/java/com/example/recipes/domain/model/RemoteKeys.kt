package com.example.recipes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val recipeId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)