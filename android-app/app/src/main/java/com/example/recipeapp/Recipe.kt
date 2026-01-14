package com.example.recipeapp

data class Recipe(
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val prep_time: String,
    val difficulty: String
)

data class RecipeResponse(
    val recipes: List<Recipe>
)

data class FoodListRequest(
    val foods: List<String>
)
