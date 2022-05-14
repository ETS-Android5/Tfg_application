package com.example.tfg_plication;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
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

public class AddRecipe extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ly;
    private Button plusIngredient;
    private View view_name;
    private EditText et;

    private static final int PERMISO_CAMARA = 0;
    private RecipeManager recipeManager;
    private ControllerDB cDB;

    private EditText name_recipe, info_recipe, num_kl;
    private ImageButton imgRecipe;
    private Spinner type_food, ingredients;
    private User user;
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        getSupportActionBar().hide();
        initValues();
        dynamicSpinners();

        this.galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //here we will handle the result of our intent
                        if (result.getResultCode() == Activity.RESULT_OK){
                            //image picked
                            //get uri of image
                            Intent data = result.getData();
                            Uri imageUri = data.getData();

                            imgRecipe.setImageURI(imageUri);
                        }
                        else {
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
        plusIngredient = findViewById(R.id.plus_ingredient);
        plusIngredient.setOnClickListener(this);
        recipeManager = new RecipeManager();
        //cFB = new ControllerFB(this);
        imgRecipe = findViewById(R.id.imgRecipe);
        name_recipe = (EditText) findViewById(R.id.name_recipe);
        info_recipe = (EditText) findViewById(R.id.info_about);
        num_kl = (EditText) findViewById(R.id.num_kal);
        type_food = (Spinner) findViewById(R.id.type_of_food);
        user = new User();

    }

    @Override
    public void onClick(View view) {
        SelectImage();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds options to the action bar if it is present.
        //getMenuInflater().inflate(R.id.addRecipe.class,menu);
        return true;
    }

    private void SelectImage(){
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(AddRecipe.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {


                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    galleryActivityResultLauncher.launch(intent);

                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryActivityResultLauncher.launch(intent);

                }
                else if (options[item].equals("Cancel")) {
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
        recipe.setRating(Float.intBitsToFloat(0));
        recipe.setTypeofFood(typeFood);

        ArrayList<RecipeIngredient> listIngredients = new ArrayList<>();
        Ingredient ingredient = null;
        for (int i = 0; i < ly.getChildCount(); i++) {
            View cricketView = ly.getChildAt(i);
            et = (EditText) cricketView.findViewById(R.id.amount);
            Spinner spinner = (Spinner) cricketView.findViewById(R.id.ingredients);
            if (view_name != null && et != null && spinner != null) {
                ingredient = new Ingredient(i + 1,spinner.getSelectedItem().toString());
                listIngredients.add(new RecipeIngredient(recipe.getId(),ingredient,Integer.parseInt(et.getText().toString())));
            }
        }
        ArrayList<RecipeIngredient> test = new ArrayList<>();
        for (int i = 0; i < listIngredients.size(); i++) {
            test.add(new RecipeIngredient(recipe.getId(),new Ingredient(listIngredients.get(i).getIngredient().getId(),listIngredients.get(i).getIngredient().getName()),listIngredients.get(i).getAmount()));
        }
        recipe.addListIngredient(test);
        cDB.addRecipe(recipe);


        Toast.makeText(this,"ShowRecipe Added!!!",Toast.LENGTH_SHORT).show();
    }

    public void chooseImage(View view) {
        ComprobarPermisos();

    }
    private void ComprobarPermisos() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            /* Ya se ha obtenido el permiso previamente
            Iniciamos Cámara*/

            SelectImage();

        } else {
            // No se tiene el permiso, es necesario pedirlo al usuario
            PedirPermisoCamara();
        }
    }
    private void PedirPermisoCamara() {
        //Comprobación 'Racional'
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {

            //Mostramos un AlertDialog al usuario explicándole la necesidad del permiso
            AlertDialog AD;
            AlertDialog.Builder ADBuilder = new AlertDialog.Builder(AddRecipe.this);
            ADBuilder.setMessage("\n" +
                    "\n" +
                    "To scan a product you need to use your device's camera. Allow 'app name' to access the camera.\n" +
                    "\n");
            ADBuilder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    /*Cuando el usuario pulse sobre el botón del AlertDialog se procede a solicitar
                     el permiso con el siguiente código:*/

                    ActivityCompat.requestPermissions(
                            AddRecipe.this,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISO_CAMARA);
                }
            });

            //Mostramos el AlertDialog
            AD = ADBuilder.create();
            AD.show();


        } else {
            /*Si no hay necesidad de una explicación racional, pasamos a solicitar el
            permiso directamente*/
            ActivityCompat.requestPermissions(
                    AddRecipe.this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISO_CAMARA);
        }


    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISO_CAMARA) {
            /* Resultado de la solicitud para permiso de cámara
             Si la solicitud es cancelada por el usuario, el método .lenght sobre el array
             'grantResults' devolverá null.*/

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Permiso concedido, podemos iniciar camara

                SelectImage();

            } else {
                /* Permiso no concedido
                 Aquí habría que explicar al usuario el por qué de este permiso
                 y volver a solicitarlo .*/
//
            }
        }
    }





}
