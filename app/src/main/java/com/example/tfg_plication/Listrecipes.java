package com.example.tfg_plication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg_plication.db.ControllerDB;

public class Listrecipes extends AppCompatActivity {
    ControllerDB controllerDB;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listrecipes);

        controllerDB = new ControllerDB(this);

        controllerDB.getAllRecipes();
    }
}