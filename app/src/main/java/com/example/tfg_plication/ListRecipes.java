package com.example.tfg_plication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tfg_plication.db.ControllerDB;

public class ListRecipes extends AppCompatActivity {

    ControllerDB controllerDB;
    ListView listRecipe;
    private ArrayAdapter<String> miAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipes);
        controllerDB = new ControllerDB(this);
        listRecipe = findViewById(R.id.listRecipe);

        /**
         *
         *      /**********                     **********/
                /**********  AVISO INFORMATIVO  **********/
                /**********                     **********
         *
         * Para recuperar la imagen de la BD simplemente este formato
                 ImageView image = findViewById(R.id.imageView);
         *       Drawable d = new BitmapDrawable(getResources(), recipe.getImg());
         *       image.setImageDrawable(d);
         *
         * @author  Adrian Fernandez
         * @version 1.0
         *
         */

        //actualizarUI();

    }

    /*private void actualizarUI(){

        if (controllerDB.getAllRecipes().size() == 0) {
            listRecipe.setAdapter(null);
        }else{
            miAdapter = new ArrayAdapter<>(this,R.layout.activity_list_view,R.id.recipeTittle,controllerDB.getAllRecipes());
            listRecipe.setAdapter(miAdapter);

        }
    }*/
}