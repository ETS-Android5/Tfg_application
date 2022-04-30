package com.example.tfg_plication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Tomato");
        ingredients.add("Carrot");
        ingredients.add("Lettuce");
        ingredients.add("Escarole");
        ingredients.add("Broccoli");
        ingredients.add("Cucumber");

        ArrayList<Integer> amount = new ArrayList<>();
        for (int i=1;i < 10;i++){
            amount.add(i);
        }

        Spinner spinner1 = findViewById(R.id.ingredient);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.support.design.R.layout.support_simple_spinner_dropdown_item,ingredients);
        spinner1.setAdapter(arrayAdapter);

        Spinner spinner2 = findViewById(R.id.numIngredients);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.support.design.R.layout.support_simple_spinner_dropdown_item,amount);
        spinner2.setAdapter(arrayAdapter2);



    }
    /*
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
