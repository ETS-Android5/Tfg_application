package com.example.tfg_plication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.tfg_plication.entity.RecipeIngredient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListRecipes extends AppCompatActivity {
    private ControllerDB cDB;
    ListView lv;
    private ArrayAdapter<Recipe> miAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipes);
        lv = findViewById(R.id.listRecipe);
        cDB = new ControllerDB(this);
        ArrayList<Recipe> arrayOfUsers = new ArrayList<Recipe>();
        for (Recipe recipe : cDB.getAllRecipes()) {
            Recipe reAux = new Recipe();
            reAux.setId(recipe.getId());
            reAux.setName(recipe.getName());
            reAux.setRecipeText(recipe.getRecipeText());
            reAux.setImg(recipe.getImg());
            reAux.setFatten(recipe.getFatten()+" kcal.");
            reAux.setTypeofFood(recipe.getTypeofFood());
            arrayOfUsers.add(reAux);
        }

        RecipeAdapter adapter = new RecipeAdapter(this, arrayOfUsers);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListRecipes.this, TestShowRecipe.class);
                intent.putExtra("idRecipe", arrayOfUsers.get(i).getId());
                startActivity(intent);
                //Toast.makeText(ListRecipes.this,"Id -->"+arrayOfUsers.get(i).getId(),Toast.LENGTH_SHORT).show();
            }
        });



        /*controllerFB.getRecipe(1, new ControllerFB.RecipeDataStatus() {
            @Override
            public void OnRecipeGetId(Long idRecipe) {

            }

            @Override
            public void getUserRecipe(List<Recipe> userRecipes) {


                startActivity(intent);
                //Toast.makeText(ListRecipes.this,"Hello",Toast.LENGTH_SHORT).show();
            }
        });
        //UpdateUI();

    }

    private void UpdateUI(){
        ArrayList<Recipe> arrayOfUsers = new ArrayList<Recipe>();


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

    }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_recipes, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle("Export Dialog")
                .setMessage("Are you sure to export those files?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                createCsv();
                            }
                        }
                )
                .setNegativeButton("Cancel", null)
                .create();
        alert.show();
        return super.onOptionsItemSelected(item);
    }

    //SEND CSV COMPLETE TO SOCIAL NETWORK
    public void createCsv(){
       StringBuilder data = new StringBuilder();
       for(Recipe recipe : cDB.getAllRecipes()){
           data.append("\n Name:"+(recipe.getName())+", ");
           data.append("How to Make: "+recipe.getRecipeText()+", ");
           data.append("Kcal: "+recipe.getFatten()+", ");
           data.append(" Ingredients: { ");
           recipe = cDB.getRecipe(recipe.getId());
           for(RecipeIngredient ingredients : recipe.getIngredients()){
               data.append(ingredients.getIngredient().getName()+", ");
               data.append(ingredients.getAmount()+", ");
           }
           data.append("}, ");
       }
       try{
           FileOutputStream out = openFileOutput( "data.csv", Context.MODE_PRIVATE);
           out.write((data.toString().getBytes()));

           Context context = getApplicationContext();
           File filelocation = new File(getFilesDir(),"data.csv");
           Uri path = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID+".provider", filelocation);
           Intent fileIntent = new Intent(Intent.ACTION_SEND);
           fileIntent.setType("text/csv");
           fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Data");
           fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
           fileIntent.putExtra(Intent.EXTRA_STREAM,path);
           startActivity(Intent.createChooser(fileIntent,"Send"));

       }
       catch(Exception e){
           e.printStackTrace();
       }
    }


    /*public void createCsv(View view) {
        createCsv();
    }*/
}