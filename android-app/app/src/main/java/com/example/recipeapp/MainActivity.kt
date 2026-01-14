package com.example.recipeapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var ingredientsInput: TextInputEditText
    private lateinit var getRecipesButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var apiService: RecipeApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ingredientsInput = findViewById(R.id.ingredientsInput)
        getRecipesButton = findViewById(R.id.getRecipesButton)
        progressBar = findViewById(R.id.progressBar)
        recipesRecyclerView = findViewById(R.id.recipesRecyclerView)

        recipesRecyclerView.layoutManager = LinearLayoutManager(this)

        apiService = RecipeApiService.create()

        getRecipesButton.setOnClickListener {
            val ingredientsText = ingredientsInput.text.toString().trim()
            if (ingredientsText.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_ingredients, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val foods = ingredientsText.split("\n")
                .map { it.trim() }
                .filter { it.isNotEmpty() }

            if (foods.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_ingredients, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            fetchRecipes(foods)
        }
    }

    private fun fetchRecipes(foods: List<String>) {
        lifecycleScope.launch {
            try {
                showLoading(true)

                val request = FoodListRequest(foods)
                val response = apiService.getRecipes(request)

                showLoading(false)

                if (response.recipes.isNotEmpty()) {
                    displayRecipes(response.recipes)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "No recipes found",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {
                showLoading(false)
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.error_network) + "\n${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }
    }

    private fun displayRecipes(recipes: List<Recipe>) {
        val adapter = RecipeAdapter(recipes)
        recipesRecyclerView.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        getRecipesButton.isEnabled = !isLoading
        ingredientsInput.isEnabled = !isLoading
    }
}
