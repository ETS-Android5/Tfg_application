package com.example.tfg_plication;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;


import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.entity.Ingredient;
import com.example.tfg_plication.entity.Recipe;
import com.example.tfg_plication.entity.RecipeIngredient;
import com.example.tfg_plication.entity.TestIngredient;
import com.example.tfg_plication.entity.User;
import com.example.tfg_plication.relation.RecipeManager;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ly;
    private Button plusIngredient;
    private View view_name;
    private EditText et;


    private RecipeManager recipeManager;
    private ControllerDB cDB;

    private EditText name_recipe, info_recipe, num_kl;
    private ImageButton imgRecipe;
    private Spinner type_food, ingredients;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        initValues();
        dynamicSpinners();
    }


    private void dynamicSpinners() {

        ArrayList<String> type = new ArrayList<>();
        type.add("Comida");
        type.add("Desayuno");
        type.add("Cena");

        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, type);
        type_food.setAdapter(arrayAdapter2);

    }

    private void initValues() {
        ly = findViewById(R.id.ly);
        plusIngredient = findViewById(R.id.plus_ingredient);
        plusIngredient.setOnClickListener(this);
        recipeManager = new RecipeManager();
        cDB = new ControllerDB(this);
        imgRecipe = findViewById(R.id.imgRecipe);
        name_recipe = (EditText) findViewById(R.id.name_recipe);
        info_recipe = (EditText) findViewById(R.id.info_about);
        num_kl = (EditText) findViewById(R.id.num_kal);
        type_food = (Spinner) findViewById(R.id.type_of_food);
        user = new User();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plus_ingredient:
                addView();
                break;
        }
    }

    private void addView() {
        view_name = getLayoutInflater().inflate(R.layout.ingredient_format, null, false);
        ingredients = view_name.findViewById(R.id.ingredients);
        ArrayList<String> test = new ArrayList<>();
        for (int i = 0; i < recipeManager.getIngredients(this).size(); i++) {
            test.add(recipeManager.getIngredients(this).get(i).getName());
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, test);
            ingredients.setAdapter(arrayAdapter);
        }
        ly.addView(view_name);

    }

    public void saveInfo(View view) {
        String name = name_recipe.getText().toString();
        String description = info_recipe.getText().toString();
        String calories = num_kl.getText().toString();
        String typeFood = type_food.getSelectedItem().toString();
        int idUser = this.getIntent().getExtras().getInt("idUser");
        user.setId(idUser);
        Bitmap bmImg = ((BitmapDrawable) imgRecipe.getDrawable()).getBitmap();
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setRecipeText(description);
        recipe.setFatten(calories);
        recipe.setUser(user);
        recipe.setImg(bmImg);
        recipe.setTypeofFood(typeFood);

        ArrayList<RecipeIngredient> listIngredients = new ArrayList<>();
        Ingredient ingredient = null;
        for (int i = 0; i < ly.getChildCount(); i++) {
            View cricketView = ly.getChildAt(i);
            et = (EditText) cricketView.findViewById(R.id.amount);
            Spinner spinner = (Spinner) cricketView.findViewById(R.id.ingredients);
            if (view_name != null && et != null && spinner != null) {
                ingredient = new Ingredient(i + 1,spinner.getSelectedItem().toString());
                listIngredients.add(new RecipeIngredient(ingredient,Integer.parseInt(et.getText().toString())));
            }
        }
        ArrayList<RecipeIngredient> test = new ArrayList<>();
        for (int i = 0; i < listIngredients.size(); i++) {
            test.add(new RecipeIngredient(new Ingredient(listIngredients.get(i).getIngredient().getId(),listIngredients.get(i).getIngredient().getName()),listIngredients.get(i).getAmount()));
        }
        recipe.addListIngredient(test);
        cDB.addRecipe(recipe);
        Toast.makeText(this,"Recipe Added!!!",Toast.LENGTH_SHORT).show();
    }




    public void chooseImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select The Application"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            imgRecipe.setImageURI(path);
        }
    }
}
