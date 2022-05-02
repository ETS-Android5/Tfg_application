package com.example.tfg_plication;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.entity.User;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonRecipe;
    ControllerDB controllerDB;
    private Button buttonBreakfast;
    private Button buttonMainCourse;
    private Button buttonDessert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBreakfast = findViewById(R.id.buttonStarters);
        buttonMainCourse = findViewById(R.id.buttonMainCourses);
        buttonDessert = findViewById(R.id.buttonDesserts);
        buttonRecipe = findViewById(R.id.allRecipes);
        controllerDB = new ControllerDB(this);

        buttonRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent (MainActivity.this,Listrecipes.class);
                startActivity(intentBack);
            }
        });
        buttonBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent (MainActivity.this,Recipe.class);
                startActivity(intentBack);
            }
        });
        buttonMainCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent (MainActivity.this,Recipe.class);
                startActivity(intentBack);
            }
        });
        buttonDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent (MainActivity.this,Recipe.class);
                startActivity(intentBack);
            }
        });
    }
}