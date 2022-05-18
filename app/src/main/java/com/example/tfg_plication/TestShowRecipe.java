package com.example.tfg_plication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private TextView textView,textView2,textView3,textView4,description,desIngredients;
    private ImageView imageView;
    private Button button;
    private LinearLayout lyExpandable,lyExpandable2;
    private CardView cw,cw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_show_recipe);
        controllerDB = new ControllerDB(this);
        description = findViewById(R.id.test001);
        button = findViewById(R.id.goBackToListView);
        imageView = findViewById(R.id.getImg_);
        textView = findViewById(R.id.getIngredients_);
        textView2 = findViewById(R.id.getDescription_);
        textView3 = findViewById(R.id.getFatten_);
        textView4 = findViewById(R.id.getRecipeName_);
        lyExpandable = findViewById(R.id.expandable);
        lyExpandable2 = findViewById(R.id.expandable2);
        desIngredients = findViewById(R.id.test002);
        cw2 = findViewById(R.id.cwExpand2);
        cw = findViewById(R.id.cwExpand);
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        int id = this.getIntent().getExtras().getInt("idRecipe");

        recipe = controllerDB.getRecipeIntoTsR(id);



        String txt = "";
        for (RecipeIngredient ri : recipe.getIngredients()) {
            txt += ri.getAmount()+" de "+ri.getIngredient().getName()+"\n";
            textView.setText(txt);
        }
        textView2.setText(recipe.getRecipeText());
        textView3.setText(recipe.getFatten()+" Kcal.");
        textView4.setText(recipe.getName());
        Drawable d = new BitmapDrawable(getResources(), recipe.getImg());
        imageView.setImageDrawable(d);


        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lyExpandable.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(cw,new AutoTransition());
                    lyExpandable.setVisibility(View.VISIBLE);
                }else{
                    TransitionManager.beginDelayedTransition(cw,new AutoTransition());
                    lyExpandable.setVisibility(View.GONE);
                }
            }
        });

        desIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lyExpandable2.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(cw2,new AutoTransition());
                    lyExpandable2.setVisibility(View.VISIBLE);
                }else{
                    TransitionManager.beginDelayedTransition(cw2,new AutoTransition());
                    lyExpandable2.setVisibility(View.GONE);
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = getIntent().getExtras().getInt("idUser");
                Intent changeSign = new Intent(TestShowRecipe.this, ListRecipes.class);
                changeSign.putExtra("returnIdToListRecipe", id);
                startActivity(changeSign);
            }
        });
    }
}
