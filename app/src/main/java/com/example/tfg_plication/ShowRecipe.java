package com.example.tfg_plication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.db.ControllerFB;
import com.example.tfg_plication.entity.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ShowRecipe extends AppCompatActivity {
    private ImageView imgRecipe;
    //RatingBar ratingBar;
    Button button,upRating;
    private LinearLayout lyExpandable;
    private CardView cw;
    private TextView stringDescription;
    private TextView nameRecipe, typeFood, calories, infoRecipe;
    private ControllerDB controllerDB;
    private RatingBar ratingBar;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);
        initValues();
        controllerDB = new ControllerDB(this);
        String val = null;
        if (this.getIntent().getExtras().getString("bf") != null) {
            val = this.getIntent().getExtras().getString("bf");
            generateRandomRecipe(val);

        } else if (this.getIntent().getExtras().getString("eat") != null) {
            val = this.getIntent().getExtras().getString("eat");
            generateRandomRecipe(val);
        } else {
            val = this.getIntent().getExtras().getString("din");
            generateRandomRecipe(val);
        }

        Recipe recipe = controllerDB.getRecipe(id);
        Drawable d = new BitmapDrawable(getResources(), recipe.getImg());
        imgRecipe.setImageDrawable(d);
        nameRecipe.setText(recipe.getName());
        infoRecipe.setText(recipe.getRecipeText());
        typeFood.setText(recipe.getTypeofFood());
        calories.setText(recipe.getFatten()+" Kcal.");
        ratingBar.setRating(recipe.getRating());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = getIntent().getExtras().getInt("idUser");
                Intent intent = new Intent(ShowRecipe.this,MainActivity.class);
                intent.putExtra("returnIdToMain", id);
                startActivity(intent);
            }
        });
        upRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controllerDB.updateRating(recipe.getRating(),ratingBar.getRating(),recipe.getId());
                Toast.makeText(ShowRecipe.this,"Score Updated!!",Toast.LENGTH_SHORT).show();
            }
        });
        stringDescription.setOnClickListener(new View.OnClickListener() {
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




        /*Recipe recipe = controllerDB.getRecipe();
         */
        //ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        //ratingBar.setRating(recipe.getRating());
        /*button = (Button) findViewById(R.id.buttonRating);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float aux = (ratingBar.getRating()+recipe.getRating())/2;
                ratingBar.setRating(aux);
            }
        });*/


        /*controllerFB.getRecipe(1, new ControllerFB.RecipeDataStatus() {
            @Override
            public void OnRecipeGetId(Long idRecipe) {

            }

            @Override
            public void getUserRecipe(List<Recipe> userRecipes) {

            }

            @Override
            public void getRecipeIngredients(Recipe recipe) {

            }

            @Override
            public void getAllRecipe(List<Recipe> allRecipe) {

            }

            @Override
            public void getRecipe(Recipe recipe) {

                Drawable d = new BitmapDrawable(getResources(), recipe.getImg());
                imgRecipe.setImageDrawable(d);
                nameRecipe.setText(recipe.getName());
                infoRecipe.setText(recipe.getRecipeText());
                typeFood.setText(recipe.getTypeofFood());
                calories.setText(recipe.getFatten());

            }
        });*/


    }

    private void generateRandomRecipe(String val) {
        ArrayList<Integer> ids = controllerDB.getIdsRecipes(val);
        Log.v("ShowRecipe", "list size--> " + ids.size());
        int amountIds = controllerDB.amountIds(val);
        Log.v("ShowRecipe", "fields--> " + ids.size());
        int rand = (int) (Math.random() * amountIds);
        id = ids.get(rand);
    }

    private void initValues() {
        imgRecipe = findViewById(R.id.img);
        nameRecipe = findViewById(R.id.getNameRecipe);
        infoRecipe = findViewById(R.id.getInfoFromRecipe);
        typeFood = findViewById(R.id.getTypeFood);
        calories = findViewById(R.id.getNumCal);
        button = findViewById(R.id.goBack);
        stringDescription = findViewById(R.id.test01);
        cw = findViewById(R.id.cwExpand);
        lyExpandable = findViewById(R.id.expandable);
        ratingBar = findViewById(R.id.getScore);
        upRating = findViewById(R.id.updateRating);
    }


    /**
     *
     /**********                     **********/
    /**********  AVISO INFORMATIVO  **********/
    /**********                     **********
     *
     * Para recuperar la imagen de la BD simplemente este formato
     ImageView image = findViewById(R.id.imageView);
     *       Drawable d = new BitmapDrawable(getResources(), recipe.getImg());
     *       image.setImageDrawable(d);
     *
     * @author Adrian Fernandez
     * @version 1.0
     *
     */


}
