package com.example.tfg_plication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.db.ControllerFB;
import com.example.tfg_plication.entity.Recipe;
import com.example.tfg_plication.entity.RecipeIngredient;

import java.util.List;

public class TestShowRecipe extends AppCompatActivity {
    private ControllerFB controllerFB;
    private Recipe recipe;
    private TextView textView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_show_recipe);
        controllerFB = new ControllerFB(this);
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        textView = findViewById(R.id.getIngredients_);
        imageView = findViewById(R.id.getImg_);
        Drawable d = new BitmapDrawable(getResources(), recipe.getImg());
        imageView.setImageDrawable(d);
        //controllerDB.show_Test_Recipe(recipe);

        controllerFB.getRecipe(1, new ControllerFB.RecipeDataStatus() {
            @Override
            public void getUserRecipe(List<Recipe> userRecipes) {

            }

            @Override
            public void getRecipeIngredients(Recipe recipe) {
                for (RecipeIngredient recipeIngredient : recipe.getIngredients()){
                    textView.setText(String.valueOf(recipeIngredient.getAmount()+" -- "+recipeIngredient.getIngredient().getName()));
                }
            }

            @Override
            public void getAllRecipe(List<Recipe> allRecipe) {

            }

            @Override
            public void getRecipe(Recipe recipe) {

            }
        });

    }
}