package com.example.tfg_plication;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;


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

import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout ly;
    private Button plusIngredient;
    private View view_name;
    private ImageView x_del_ingredient;
    private EditText et;




    private RecipeManager recipeManager;
    //ControllerDB controllerDB;
    private ImageButton imgRecipe;
    private Bitmap bmImg;
    private EditText txt_recipe, info_recipe, num_kl, ingredient;
    private Spinner type_food, ingredients;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        initValues();
        dynamicSpinners();
        //addRecipe();
        //controllerDB = new ControllerDB(this);
        //Toast.makeText(this, "" + controllerDB.getAllIngredient(), Toast.LENGTH_SHORT).show();

    }

    private void addRecipe() {
        /*int idUser = this.getIntent().getExtras().getInt("USER_I_NEED");
        user.setId(idUser);
        Recipe recipe = new Recipe(1, txt_recipe.getText().toString(), info_recipe.getText().toString(), num_kl.getText().toString(), null, user, bmImg, type_food.getSelectedItem().toString());
        ArrayList<RecipeIngredient> listIngredients = new ArrayList<>();
        //ingredient.getText().toString();
        listIngredients.add(new RecipeIngredient(new Ingredient(1, ingredients.getSelectedItem().toString()), recipe, seekBar.getProgress()));

        recipe.addListIngredient(listIngredients);
        recipeManager.addRecipeToDB(recipe,this);*/
    }


    private void dynamicSpinners() {

        ArrayList<String> type = new ArrayList<>();
        type.add("Comida");
        type.add("Desayuno");
        type.add("Cena");

        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, type);
        type_food.setAdapter(arrayAdapter2);

        /*ArrayList<String> test = new ArrayList<>();
        for(int i = 0;i < recipeManager.getIngredients(this).size();i++){
            test.add(recipeManager.getIngredients(this).get(i).getName());
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, test);
            ingredients.setAdapter(arrayAdapter);
        }*/

    }

    private void initValues() {
        ly = findViewById(R.id.ly);
        plusIngredient = findViewById(R.id.plus_ingredient);
        plusIngredient.setOnClickListener(this);


        recipeManager = new RecipeManager();
        //ControllerDB controllerDB = new ControllerDB(this);
        //imgRecipe = findViewById(R.id.imgRecipe);
        //bmImg = ((BitmapDrawable) imgRecipe.getDrawable()).getBitmap();
        //txt_recipe = (EditText) findViewById(R.id.name_recipe);
        //info_recipe = (EditText) findViewById(R.id.info_about);
        //num_kl = (EditText) findViewById(R.id.num_kal);
        type_food = (Spinner) findViewById(R.id.type_of_food);
        //ingredient = (EditText) findViewById(R.id.orNewIngredient);
        //user = new User();
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
        for(int i = 0;i < recipeManager.getIngredients(this).size();i++){
            test.add(recipeManager.getIngredients(this).get(i).getName());
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, test);
            ingredients.setAdapter(arrayAdapter);
        }
        ly.addView(view_name);
        /*spinnerTeam = (Spinner) view_name.findViewById(R.id.things);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, teamList);
        spinnerTeam.setAdapter(arrayAdapter);*/

        /*
        for(int i = 0;i < recipeManager.getIngredients(this).size();i++){
            test.add(recipeManager.getIngredients(this).get(i).getName());
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, test);
            ingredients.setAdapter(arrayAdapter);
        }*/
    }

    public void saveInfo(View view){
        //EditText txt_recipe = (EditText) findViewById(R.id.name_recipe);
        //Bitmap getBitMap = ((BitmapDrawable)imgBtn1.getDrawable()).getBitmap();
        ArrayList<TestIngredient> lIngredients = new ArrayList<>();
        Log.v("DynamicLY", "Num: " + ly.getChildCount());
        for (int i = 0; i < ly.getChildCount(); i++) {
            View cricketView = ly.getChildAt(i);
            et = (EditText) cricketView.findViewById(R.id.amount);
            Spinner spinner = (Spinner) cricketView.findViewById(R.id.ingredients);
            x_del_ingredient = (ImageView) cricketView.findViewById(R.id.remove);
            if (view_name != null && et != null) {
                lIngredients.add(new TestIngredient(i + 1, et.getText().toString(), spinner.getSelectedItem().toString()));
            }
        }
        for (TestIngredient ig : lIngredients) {
            Toast.makeText(this, ig.getId() + "-" + ig.getIngredient() + "-" + ig.getAmount(), Toast.LENGTH_SHORT).show();
        }
    }


    /*
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.support.design.R.layout.support_simple_spinner_dropdown_item,ingredients);


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,ingredients);

        spinner1.setAdapter(arrayAdapter);

        Spinner spinner2 = findViewById(R.id.numIngredients);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,amount);
        spinner2.setAdapter(arrayAdapter2);

        //listIngredients.add(new RecipeIngredient(new Ingredient(2, "Salmon"), recipe, 4));
        //listIngredients.add(new RecipeIngredient(new Ingredient(3, "Rice"), recipe, 5));
        //listIngredients.add(new RecipeIngredient(new Ingredient(4, "Wasabi"), recipe, 5));

    }

    public void saveInfo(View view){

        EditText txt_recipe = (EditText) findViewById(R.id.name_recipe);
        Bitmap getBitMap = ((BitmapDrawable)imgBtn1.getDrawable()).getBitmap();

    }
    public void chooseImage (View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("image/*");
        startActivityForResult(intent.createChooser(intent,"Select The Application"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri path = data.getData();
            imgBtn1.setImageURI(path);
        }
    }*/
}
