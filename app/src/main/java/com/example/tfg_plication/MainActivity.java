package com.example.tfg_plication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBreakfast = findViewById(R.id.buttonStarters);
        buttonMainCourse = findViewById(R.id.buttonMainCourses);
        buttonDessert = findViewById(R.id.buttonDesserts);
        buttonRecipe = findViewById(R.id.allRecipes);
        controllerFB = new ControllerFB(this);
        buttonAddRecipe = findViewById(R.id.addRecipe);

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
                Intent intentBreakfast = new Intent (MainActivity.this, ShowRecipe.class);
                startActivity(intentBreakfast);
            }
        });
        buttonMainCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMainCourse = new Intent (MainActivity.this, ShowRecipe.class);
                startActivity(intentMainCourse);
            }
        });
        buttonDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDessert = new Intent (MainActivity.this, ShowRecipe.class);
                startActivity(intentDessert);
            }
        });

    }
}