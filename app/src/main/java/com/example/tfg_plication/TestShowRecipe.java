package com.example.tfg_plication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.entity.Recipe;
import com.example.tfg_plication.entity.RecipeIngredient;

public class TestShowRecipe extends AppCompatActivity {
    private ControllerDB controllerDB;
    private Recipe recipe;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_show_recipe);
        controllerDB = new ControllerDB(this);
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        textView = findViewById(R.id.getIngredients_);
        //controllerDB.show_Test_Recipe(recipe);


        for (RecipeIngredient recipeIngredient : controllerDB.show_Test_Recipe(recipe).getIngredients()){
            textView.setText(String.valueOf(recipeIngredient.getAmount()+" -- "+recipeIngredient.getIngredient().getName()));
        }
    }
}