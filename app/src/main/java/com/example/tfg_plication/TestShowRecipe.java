package com.example.tfg_plication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_show_recipe);
        controllerDB = new ControllerDB(this);
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        textView = findViewById(R.id.getIngredients_);
        Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("BitmapImage");
        //imageView = findViewById(R.id.getImg_);
        //Drawable d = new BitmapDrawable(getResources(), recipe.getImg());
        //imageView.setImageDrawable(d);
        String txt = "";
        for (RecipeIngredient recipeIngredient : controllerDB.getRecipeIngredient(recipe).getIngredients()){
            txt += String.valueOf(recipeIngredient.getAmount()+" -- "+recipeIngredient.getIngredient().getName())+"\n";
            textView.setText(txt);
        }
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