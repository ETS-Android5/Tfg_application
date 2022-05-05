package com.example.tfg_plication.entity;

import com.example.tfg_plication.entity.Ingredient;
public class RecipeIngredient {
    private Ingredient ingredient;
    private int amount;

    public RecipeIngredient(){}
    public RecipeIngredient(Ingredient ingredient,int amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
