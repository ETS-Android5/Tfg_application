package com.example.tfg_plication;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.db.ControllerFB;

public class MainActivity extends AppCompatActivity {

    private Button buttonRecipe;
    ControllerFB controllerFB;
    private Button buttonBreakfast;
    private Button buttonMainCourse;
    private Button buttonDessert;
    private Button buttonAddRecipe;
    private final String FONT_TITTLE = "StreetExplorer.otf";
    private Typeface font;
    private TextView app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBreakfast = findViewById(R.id.buttonStarters);
        buttonMainCourse = findViewById(R.id.buttonMainCourses);
        buttonDessert = findViewById(R.id.buttonDesserts);
        buttonRecipe = findViewById(R.id.allRecipes);
        //controllerFB = new ControllerFB(this);
        buttonAddRecipe = findViewById(R.id.addRecipe);

        //updateFont();

        int idUser = this.getIntent().getExtras().getInt("idUser");

        buttonRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentListRecipes = new Intent (MainActivity.this,ListRecipes.class);
                startActivity(intentListRecipes);
            }
        });

        buttonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentListRecipes = new Intent (MainActivity.this,AddRecipe.class);
                intentListRecipes.putExtra("idUser", idUser);
                startActivity(intentListRecipes);
            }
        });

        buttonBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int ID_USER  = getIntent().getExtras().getInt("idUser");
                Intent intentBreakfast = new Intent (MainActivity.this, ShowRecipe.class);
                if (ID_USER == 0){
                    ID_USER = getIntent().getExtras().getInt("returnIdToMain");
                }
                intentBreakfast.putExtra("idUser", ID_USER);
                intentBreakfast.putExtra("bf","Desayuno");
                startActivity(intentBreakfast);
            }
        });
        buttonMainCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ID_USER  = getIntent().getExtras().getInt("idUser");
                Intent intentBreakfast = new Intent (MainActivity.this, ShowRecipe.class);
                if (ID_USER == 0){
                    ID_USER = getIntent().getExtras().getInt("returnIdToMain");
                }
                intentBreakfast.putExtra("idUser", ID_USER);
                intentBreakfast.putExtra("eat","Comida");
                startActivity(intentBreakfast);
            }
        });
        buttonDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ID_USER  = getIntent().getExtras().getInt("idUser");
                Intent dinner = new Intent (MainActivity.this, ShowRecipe.class);
                if (ID_USER == 0){
                    ID_USER = getIntent().getExtras().getInt("returnIdToMain");
                }
                dinner.putExtra("idUser", ID_USER);
                dinner.putExtra("din","Cena");
                startActivity(dinner);
            }
        });

    }

    private void updateFont() {
        font = Typeface.createFromAsset(getAssets(), FONT_TITTLE);
        app_name = (TextView) findViewById(R.id.tws);
        app_name.setTypeface(font);
    }
}