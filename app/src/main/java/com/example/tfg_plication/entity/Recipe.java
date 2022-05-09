package com.example.tfg_plication.entity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.tfg_plication.entity.Ingredient;
import com.example.tfg_plication.entity.User;

import java.io.Serializable;
import java.util.List;

<<<<<<< Updated upstream
public class Recipe implements Serializable {
    private int id;
=======
public class Recipe {
    private long id;
>>>>>>> Stashed changes

    private User user;
    String name;
    String recipeText;
    String fatten;
    String typeofFood;
    Bitmap img;
    Drawable convertImg;
    private List <RecipeIngredient> recipeIngredients;

    public Drawable getConvertImg() {
        return convertImg;
    }

    public void setConvertImg(Bitmap bitmap) {

    }

    public Recipe(){}
    public Recipe(long id, String name, String recipeText, String fatten,List<RecipeIngredient> recipeIngredients,User user, Bitmap img,String typeofFood   ) {
        this.id = id;
        this.name = name;
        this.recipeText = recipeText;
        this.fatten = fatten;
        this.typeofFood=typeofFood;
        this.recipeIngredients = recipeIngredients;
        this.user=user;
        this.img=img;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    public String getTypeofFood() {
        return typeofFood;
    }

    public void setTypeofFood(String typeofFood) {
        this.typeofFood = typeofFood;
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
    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

}
