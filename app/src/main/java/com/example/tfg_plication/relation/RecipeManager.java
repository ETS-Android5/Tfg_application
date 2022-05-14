package com.example.tfg_plication.relation;

import android.content.Context;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.entity.Ingredient;
import com.example.tfg_plication.entity.Recipe;
import com.example.tfg_plication.entity.RecipeIngredient;
import com.example.tfg_plication.entity.User;

import java.util.List;


public class RecipeManager {
    private ControllerDB controllerDB;

    public List<Ingredient> getIngredients(Context context){
        controllerDB = new ControllerDB( context);
        return controllerDB.getAllIngredient();
    }
    public void addIngredient(String name){
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);

    }
    public void addIngredientDB(Ingredient ingredient,Context context){
        controllerDB = new ControllerDB( context);
        controllerDB.addIngredient(ingredient);
    }
    public void addRecipe(String name, String recipeText, User idUser, List<RecipeIngredient> ingredients){
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setUser(idUser);
        recipe.setRecipeText(recipeText);
        recipe.addListIngredient(ingredients);
    }
    public List<RecipeIngredient> addToListRecipeIngredient(Ingredient  ingredient,int amount,List<RecipeIngredient> ingredients/*,Recipe recipe*/){
        RecipeIngredient recipeIngredient = new RecipeIngredient(/*recipe.getId(),*/ingredient,amount);
        ingredients.add(recipeIngredient);
        return ingredients;
    }
    public void deleteRecipe(Recipe recipe,Context context){
        controllerDB = new ControllerDB( context);
        controllerDB.deleteRecipe(recipe);
    }
    public List<Recipe> allRecipeUser(User user,Context context){
        controllerDB = new ControllerDB( context);
        return controllerDB.getRecipes(user);
    }
    /*public ShowRecipe getRecipeIngredient(ShowRecipe recipe,Context context){
        controllerDB = new ControllerDB( context);
        return controllerDB.getRecipeIngredient(recipe);
    }*/
    public void addRecipeToDB(Recipe recipe, Context context){
        controllerDB = new ControllerDB( context);
        controllerDB.addRecipe(recipe);
    }

}
