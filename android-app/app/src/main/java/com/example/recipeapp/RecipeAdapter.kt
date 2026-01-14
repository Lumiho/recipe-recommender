package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeNameText: TextView = view.findViewById(R.id.recipeNameText)
        val prepTimeText: TextView = view.findViewById(R.id.prepTimeText)
        val difficultyText: TextView = view.findViewById(R.id.difficultyText)
        val ingredientsText: TextView = view.findViewById(R.id.ingredientsText)
        val instructionsText: TextView = view.findViewById(R.id.instructionsText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.recipeNameText.text = recipe.name
        holder.prepTimeText.text = "Prep Time: ${recipe.prep_time}"
        holder.difficultyText.text = "Difficulty: ${recipe.difficulty}"

        val ingredientsFormatted = recipe.ingredients.joinToString("\n") { "• $it" }
        holder.ingredientsText.text = ingredientsFormatted

        val instructionsFormatted = recipe.instructions.mapIndexed { index, instruction ->
            "${index + 1}. $instruction"
        }.joinToString("\n\n")
        holder.instructionsText.text = instructionsFormatted
    }

    override fun getItemCount() = recipes.size
}
