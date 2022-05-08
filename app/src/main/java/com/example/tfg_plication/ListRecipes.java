package com.example.tfg_plication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.entity.Recipe;
import com.example.tfg_plication.entity.RecipeAdapter;

import java.util.ArrayList;

public class ListRecipes extends AppCompatActivity {
    ControllerDB controllerDB;
    ListView lv;
    private ArrayAdapter<Recipe> miAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipes);
        controllerDB = new ControllerDB(this);
        lv = findViewById(R.id.listRecipe);

        UpdateUI();

    }

    private void UpdateUI(){
        lv = findViewById(R.id.listRecipe);
        controllerDB = new ControllerDB(this);
        ArrayList<Recipe> arrayOfUsers = new ArrayList<Recipe>();
        for (Recipe recipe : controllerDB.getAllRecipes()) {
            Recipe reAux = new Recipe();
            reAux.setConvertImg(reAux.getImg());
            //Drawable d = new BitmapDrawable(getResources(), reAux.getImg());
            //reAux.getImg().setImageDrawable(d);
            Toast.makeText(this,"-->"+recipe.getImg(),Toast.LENGTH_LONG).show();
            reAux.setName(recipe.getName());
            reAux.setRecipeText(recipe.getRecipeText());
            reAux.setFatten(recipe.getFatten());
            reAux.setTypeofFood(recipe.getTypeofFood());
            arrayOfUsers.add(reAux);
        }
        RecipeAdapter adapter = new RecipeAdapter(this, arrayOfUsers);
        lv.setAdapter(adapter);
    }
}