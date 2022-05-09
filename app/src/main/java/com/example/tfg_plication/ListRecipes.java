package com.example.tfg_plication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.db.ControllerFB;
import com.example.tfg_plication.entity.Recipe;
import com.example.tfg_plication.entity.RecipeAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListRecipes extends AppCompatActivity {
    ControllerFB controllerFB;
    ListView lv;
    private ArrayAdapter<Recipe> miAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipes);
        controllerFB = new ControllerFB(this);
        lv = findViewById(R.id.listRecipe);

        UpdateUI();

    }

    private void UpdateUI(){
        lv = findViewById(R.id.listRecipe);
        controllerFB = new ControllerFB(this);
        ArrayList<Recipe> arrayOfUsers = new ArrayList<Recipe>();

        controllerFB.getRecipe(1, new ControllerFB.RecipeDataStatus() {
            @Override
            public void getUserRecipe(List<Recipe> userRecipes) {

            }

            @Override
            public void getRecipeIngredients(Recipe recipe) {

            }

            @Override
            public void getAllRecipe(List<Recipe> allRecipe) {
                for (Recipe recipe : allRecipe) {
                    Recipe reAux = new Recipe();
                    reAux.setId(recipe.getId());
                    reAux.setConvertImg(reAux.getImg());
                    //Drawable d = new BitmapDrawable(getResources(), reAux.getImg());
                    //reAux.getImg().setImageDrawable(d);
                    //Toast.makeText(this,"-->"+recipe.getImg(),Toast.LENGTH_LONG).show();
                    reAux.setName(recipe.getName());
                    reAux.setRecipeText(recipe.getRecipeText());
                    reAux.setFatten(recipe.getFatten());
                    reAux.setTypeofFood(recipe.getTypeofFood());
                    //Toast.makeText(this,"-->"+recipe.getIngredients(),Toast.LENGTH_LONG).show();
                    //reAux.addListIngredient(recipe.getIngredients());
                    //controllerDB.show_Test_Recipe(recipe);
                    arrayOfUsers.add(reAux);
                }
            }

            @Override
            public void getRecipe(Recipe recipe) {

            }
        });


        RecipeAdapter adapter = new RecipeAdapter(this, arrayOfUsers);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListRecipes.this,TestShowRecipe.class);
                intent.putExtra("recipe",arrayOfUsers.get(i));
                startActivity(intent);
            }
        });
    }
}