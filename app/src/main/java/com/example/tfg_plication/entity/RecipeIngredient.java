package com.example.tfg_plication.entity;

import com.example.tfg_plication.entity.Ingredient;
public class RecipeIngredient {
    private Ingredient ingredient;
    private Recipe recipe;
    private int amount;

    public RecipeIngredient(){}
    public RecipeIngredient(Ingredient ingredient, Recipe recipe, int amount) {
        this.ingredient = ingredient;
        this.recipe = recipe;
        this.amount = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
