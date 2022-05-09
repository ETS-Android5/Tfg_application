package com.example.tfg_plication.entity;


public class RecipeIngredient {


    private long recipeId;
    private Ingredient ingredient;
    private Recipe recipe;
    private int amount;


    public RecipeIngredient(){}
    public RecipeIngredient(long recipeId, Ingredient ingredient,int amount) {
        this.recipeId = recipeId;
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        recipeId = recipeId;
    }

}
