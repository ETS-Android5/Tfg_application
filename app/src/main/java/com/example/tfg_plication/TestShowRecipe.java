package com.example.tfg_plication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.db.ControllerFB;
import com.example.tfg_plication.entity.Ingredient;
import com.example.tfg_plication.entity.Recipe;
import com.example.tfg_plication.entity.RecipeIngredient;

import java.util.List;

public class TestShowRecipe extends AppCompatActivity {
    private ControllerDB controllerDB;
    private Recipe recipe;
    private TextView textView;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_show_recipe);
        controllerDB = new ControllerDB(this);
        button = findViewById(R.id.goBackToListView);
        imageView = findViewById(R.id.getImg_);
        textView = findViewById(R.id.getIngredients_);
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        int id = this.getIntent().getExtras().getInt("idRecipe");

        recipe = controllerDB.getRecipeIntoTsR(id);
        String txt = "";
        for (RecipeIngredient ri : recipe.getIngredients()) {
            txt += ri.getAmount()+" -- "+ri.getIngredient().getName()+"\n";
            //Toast.makeText(this,"Ingredients--> "+ri.getIngredient().getName()+"--"+ri.getAmount(),Toast.LENGTH_SHORT).show();
            textView.setText(txt);
        }

        Drawable d = new BitmapDrawable(getResources(), recipe.getImg());
        imageView.setImageDrawable(d);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = getIntent().getExtras().getInt("idUser");
                Intent changeSign = new Intent(TestShowRecipe.this, ListRecipes.class);
                changeSign.putExtra("returnIdToListRecipe", id);
                startActivity(changeSign);
            }
        });
        /*
        *       int id = getIntent().getExtras().getInt("idUser");
                Intent intent = new Intent(ListRecipes.this, MainActivity.class);
                intent.putExtra("returnIdToMain", id);
                startActivity(intent);
        *
        * */
    }
}
