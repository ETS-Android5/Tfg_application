package com.example.tfg_plication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.db.ControllerFB;
import com.example.tfg_plication.entity.Ingredient;
import com.example.tfg_plication.entity.Recipe;
import com.example.tfg_plication.entity.RecipeIngredient;

import java.util.List;

public class TestShowRecipe extends AppCompatActivity {
    private ControllerDB controllerDB;
    private Recipe recipe;
    private TextView textView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_show_recipe);
        controllerDB = new ControllerDB(this);
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        textView = findViewById(R.id.getIngredients_);
        button = findViewById(R.id.goBackToListView);
        String txt = "";
        controllerDB.getRecipeIngredient(recipe);
        for (RecipeIngredient recipeIngredient : controllerDB.getRecipeIngredient(recipe).getIngredients()){
            txt += String.valueOf(recipeIngredient.getAmount()+" -- "+recipeIngredient.getIngredient().getName())+"\n";
            textView.setText(txt);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeSign = new Intent(TestShowRecipe.this, ListRecipes.class);
                startActivity(changeSign);
            }
        });

        /*
        * for (RecipeIngredient recipeIngredient : recipe.getIngredients()){
                    textView.setText(String.valueOf(recipeIngredient.getAmount()+" -- "+recipeIngredient.getIngredient().getName()));
                }
        * */


        //Log.v("TestShowRecipe","-->"+controllerDB.show_Test_Recipe(recipe).getIngredients().size());


        /*for (int i = 0;i < controllerDB.show_Test_Recipe(recipe).getIngredients().size();i++){
            textView.setText(String.valueOf(recipeIngredient.getAmount()+" -- "+recipeIngredient.getIngredient().getName()));
        }*/


        /*for (RecipeIngredient recipeIngredient : controllerDB.show_Test_Recipe(recipe).getIngredients()){

        }*/

        /*controllerFB.getRecipe(1, new ControllerFB.RecipeDataStatus() {
            @Override
            public void OnRecipeGetId(Long idRecipe) {

            }

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
        });*/

    }
}