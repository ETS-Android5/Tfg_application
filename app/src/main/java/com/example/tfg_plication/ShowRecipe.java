package com.example.tfg_plication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.db.ControllerFB;
import com.example.tfg_plication.entity.Recipe;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ShowRecipe extends AppCompatActivity {
    private ImageView imgRecipe;
    RatingBar ratingBar;
    Button button;
    private TextView nameRecipe, typeFood, calories,infoRecipe;
    private ControllerDB controllerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);
        initValues();
        controllerDB = new ControllerDB(this);
        Recipe recipe = controllerDB.getRecipe();
        Drawable d = new BitmapDrawable(getResources(), recipe.getImg());
        imgRecipe.setImageDrawable(d);
        nameRecipe.setText(recipe.getName());
        infoRecipe.setText(recipe.getRecipeText());
        typeFood.setText(recipe.getTypeofFood());
        calories.setText(recipe.getFatten());
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(recipe.getRating());
        button = (Button) findViewById(R.id.buttonRating);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float aux = (ratingBar.getRating()+recipe.getRating())/2;
                ratingBar.setRating(aux);
            }
        });


        /*controllerFB.getRecipe(1, new ControllerFB.RecipeDataStatus() {
            @Override
            public void OnRecipeGetId(Long idRecipe) {

            }

            @Override
            public void getUserRecipe(List<Recipe> userRecipes) {

            }

            @Override
            public void getRecipeIngredients(Recipe recipe) {

            }

            @Override
            public void getAllRecipe(List<Recipe> allRecipe) {

            }

            @Override
            public void getRecipe(Recipe recipe) {

                Drawable d = new BitmapDrawable(getResources(), recipe.getImg());
                imgRecipe.setImageDrawable(d);
                nameRecipe.setText(recipe.getName());
                infoRecipe.setText(recipe.getRecipeText());
                typeFood.setText(recipe.getTypeofFood());
                calories.setText(recipe.getFatten());

            }
        });*/


    }

    private void initValues() {
        imgRecipe = findViewById(R.id.img);
        nameRecipe = findViewById(R.id.getNameRecipe);
        infoRecipe = findViewById(R.id.getInfoFromRecipe);
        typeFood = findViewById(R.id.getTypeFood);
        calories = findViewById(R.id.getNumCal);
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
