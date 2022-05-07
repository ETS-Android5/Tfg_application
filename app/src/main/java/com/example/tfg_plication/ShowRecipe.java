package com.example.tfg_plication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.entity.Recipe;

public class ShowRecipe extends AppCompatActivity {
    private ImageView imgRecipe;
    private TextView nameRecipe, typeFood, calories;
    private EditText infoRecipe,showIngredients;
    private ControllerDB controllerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);
        initValues();
        controllerDB = new ControllerDB(this);
        Recipe recipeAux = controllerDB.getRecipe();
        Drawable d = new BitmapDrawable(getResources(), recipeAux.getImg());
        imgRecipe.setImageDrawable(d);
        nameRecipe.setText(recipeAux.getName());
        infoRecipe.setText(recipeAux.getRecipeText());
        typeFood.setText(recipeAux.getTypeofFood());
        calories.setText(recipeAux.getFatten());

    }

    private void initValues() {
        imgRecipe = findViewById(R.id.img);
        nameRecipe = findViewById(R.id.getNameRecipe);
        infoRecipe = findViewById(R.id.getInfoFromRecipe);
        typeFood = findViewById(R.id.getTypeFood);
        calories = findViewById(R.id.getNumCal);
        showIngredients = findViewById(R.id.getIngredientsRecipe);
    }





    /**
     *
     /**********                     **********/
    /**********  AVISO INFORMATIVO  **********/
    /**********                     **********
     *
     * Para recuperar la imagen de la BD simplemente este formato
     ImageView image = findViewById(R.id.imageView);
     *       Drawable d = new BitmapDrawable(getResources(), recipe.getImg());
     *       image.setImageDrawable(d);
     *
     * @author Adrian Fernandez
     * @version 1.0
     *
     */


}