package com.example.tfg_plication;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.hardware.Camera;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
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
import androidx.core.app.ActivityCompat;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.db.ControllerFB;
import com.example.tfg_plication.entity.Ingredient;
import com.example.tfg_plication.entity.Recipe;
import com.example.tfg_plication.entity.RecipeIngredient;
import com.example.tfg_plication.entity.TestIngredient;
import com.example.tfg_plication.entity.User;
import com.example.tfg_plication.relation.RecipeManager;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddRecipe extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ly;
    private Button plusIngredient;
    private View view_name;
    private EditText et;

    private static final int PERMISO_CAMARA = 0;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private RecipeManager recipeManager;
    private ControllerDB cDB;
    private Button button;
    //private List<Ingredient> ingredients;
    private EditText name_recipe, info_recipe, num_kl;
    private ImageButton imgRecipe;
    private Spinner type_food, ingredients;
    private User user;
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher;
    private ActivityResultLauncher<Intent> galleryAtcitivtyResultfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        getSupportActionBar().hide();
        initValues();
        dynamicSpinners();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = getIntent().getExtras().getInt("idUser");
                Intent intent = new Intent(AddRecipe.this, MainActivity.class);
                intent.putExtra("returnIdToMain", id);
                startActivity(intent);
            }
        });

        this.galleryAtcitivtyResultfile = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){

                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();

                            Uri selectedImage = data.getData();
                            imgRecipe.setImageURI(selectedImage);

                        }
                        }
                    });


        this.galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //here we will handle the result of our intent
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();
                            Bitmap photo = (Bitmap)data.getExtras().get("data");


                            imgRecipe.setImageBitmap(photo);

                        } else {
                            //cancelled
                            Toast.makeText(AddRecipe.this, "Cancelled...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

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
        button = findViewById(R.id.btn_return);
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
                //SelectImage();
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds options to the action bar if it is present.
        //getMenuInflater().inflate(R.id.addRecipe.class,menu);
        return true;
    }

    private void SelectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddRecipe.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);

                    //File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    galleryActivityResultLauncher.launch(intent);


                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivity(intent);

                    galleryAtcitivtyResultfile.launch(intent);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();

    }







            public void saveInfo(View view) {
        String name = name_recipe.getText().toString();
        String description = info_recipe.getText().toString();
        String calories = num_kl.getText().toString();
        String typeFood = type_food.getSelectedItem().toString();
        long idUser = this.getIntent().getExtras().getLong("idUser");
        user.setId(idUser);
        Bitmap bmImg = ((BitmapDrawable) imgRecipe.getDrawable()).getBitmap();
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setRecipeText(description);
        recipe.setFatten(calories);
        recipe.setUser(user);
        recipe.setImg(bmImg);
        //recipe.setRating(Float.intBitsToFloat(0));
        recipe.setTypeofFood(typeFood);

        ArrayList<RecipeIngredient> listIngredients = new ArrayList<>();
        Ingredient ingredient = null;
        for (int i = 0; i < ly.getChildCount(); i++) {
            View cricketView = ly.getChildAt(i);
            et = (EditText) cricketView.findViewById(R.id.amount);
            Spinner spinner = (Spinner) cricketView.findViewById(R.id.ingredients);
            if (view_name != null && et != null && spinner != null) {
                ingredient = new Ingredient(i + 1, spinner.getSelectedItem().toString());
                listIngredients.add(new RecipeIngredient(ingredient, Integer.parseInt(et.getText().toString())));
            }
        }
        ArrayList<RecipeIngredient> test = new ArrayList<>();
        for (int i = 0; i < listIngredients.size(); i++) {
            test.add(new RecipeIngredient(new Ingredient(listIngredients.get(i).getIngredient().getId(), listIngredients.get(i).getIngredient().getName()), listIngredients.get(i).getAmount()));
        }
        recipe.addListIngredient(test);
        cDB.addRecipe(recipe);
        Toast.makeText(this, "ShowRecipe Added!!!", Toast.LENGTH_SHORT).show();
    }

    public void chooseImage(View view) {
        ComprobarPermisos();
        //SelectImage();
    }

    private void ComprobarPermisos() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            Toast.makeText(this, "This version is not Android 6 or later " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();

        } else {

            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CAMERA);

            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[] {Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);

                Toast.makeText(this, "Requesting permissions", Toast.LENGTH_LONG).show();

            }else if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED){

                Toast.makeText(this, "The permissions are already granted ", Toast.LENGTH_LONG).show();
                SelectImage();

            }

        }

        return;
    }




    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(REQUEST_CODE_ASK_PERMISSIONS == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "OK Permissions granted ! " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();
                SelectImage();
            } else {
                Toast.makeText(this, "Permissions are not granted ! " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
