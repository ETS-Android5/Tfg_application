package com.example.tfg_plication.entity;
import com.example.tfg_plication.entity.Ingredient;

import java.util.List;

public class Recipe {
    private int id;



    private User user;
    private String name;
    private String recipeText;
    private String fatten;
    private List <RecipeIngredient> recipeIngredients;




    public Recipe(){}
    public Recipe(int id, String name, String recipeText, String fatten,List<RecipeIngredient> recipeIngredients,User user   ) {
        this.id = id;
        this.name = name;
        this.recipeText = recipeText;
        this.fatten = fatten;
        this.recipeIngredients = recipeIngredients;
        this.user=user;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipeText() {
        return recipeText;
    }

    public void setRecipeText(String recipeText) {
        this.recipeText = recipeText;
    }

    public String getFatten() {
        return fatten;
    }

    public void setFatten(String fatten) {
        this.fatten = fatten;
    }

    public List<RecipeIngredient> getIngredients() {
        return recipeIngredients;
    }
    public void addIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredients.add(recipeIngredient);
    }
    public void deleteIngredient(RecipeIngredient recipeIngredient){
        for(int i=0; i<=recipeIngredients.size(); i++){
            if(recipeIngredients.get(i).getIngredient().getId()==recipeIngredient.getIngredient().getId()){
                recipeIngredients.remove(i);
            }
        }
    }
    public void addListIngredient(List<RecipeIngredient> recipeIngredients){
        this.recipeIngredients = recipeIngredients;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
