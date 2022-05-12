package com.example.tfg_plication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tfg_plication.Login;
import com.example.tfg_plication.entity.Ingredient;
import com.example.tfg_plication.entity.Recipe;
import com.example.tfg_plication.entity.RecipeIngredient;
import com.example.tfg_plication.entity.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ControllerFB {
    /*FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private long initValueUser=0;
    private long initValueIngredient=0;
    private long initValueRecipe=0;


    public ControllerFB(Context context)
    {
        FirebaseApp.initializeApp(context);

        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    public interface UserDataStatus
    {
        void onUserExist();
        void onUserDoesntExist();

    }
    public interface PassDataStatus
    {
        void onPassExist(User userAux);
        void onPassDoesntExist();
    }
    public interface GetUser
    {
        void getUser(User user);
    }
    public interface IngredientDataStatus
    {
        void getIngredient(Ingredient ingredient);
        void getIngredients(List<Ingredient> ingredients);

    }
    public interface RecipeDataStatus
    {
        void getUserRecipe(List<Recipe> userRecipes);
        void getRecipeIngredients(Recipe recipe);
        void getAllRecipe(List<Recipe> allRecipe);
        void getRecipe(Recipe recipe);
    }

    public void onCreate(SQLiteDatabase db) {

    }


    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Tomate')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Pollo')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Harina')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Huevo')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Leche')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Queso Crema')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Lechuga')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Zanahoria')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Limon')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('levadura')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Levadura')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Sal')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Coliflor')");

    }

    public void checkIfUserExists(User user,UserDataStatus dataStatus) {



        DatabaseReference reff = firebaseDatabase.getReference().child("User");
        Query query = reff.orderByChild("name").equalTo(user.getName());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User userAux = snapshot.getValue(User.class);
                Log.d("mensaje",userAux.getName().toString());
                user.setId(userAux.getId());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    dataStatus.onUserExist();
                }else
                {
                    dataStatus.onUserDoesntExist();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkIfPassExists(User user,PassDataStatus dataStatus) {

        DatabaseReference reff = firebaseDatabase.getReference().child("User");
        Query query = reff.orderByChild("pass").equalTo(user.getPass());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    User userAux = snapshot.getValue(User.class);
                    dataStatus.onPassExist(userAux);
                }else
                {
                    dataStatus.onPassDoesntExist();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getUser(long id,GetUser dataStatus){


        DatabaseReference reff = firebaseDatabase.getReference().child("User");
        Query query = reff.orderByKey().equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userAux =  snapshot.getValue(User.class);
                dataStatus.getUser(userAux);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void insertNewUser(User user) {
        DatabaseReference reff = firebaseDatabase.getReference().child("User");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    initValueUser = (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        user.setId(initValueUser+1);
        databaseReference.child("User").child(String.valueOf(user.getId())).setValue(user);

    }

    public int getCantRegisters(User user) {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c1 = ref_db.rawQuery("SELECT ID FROM USERS WHERE USER=?", new String[]{user.getName()});
        c1.moveToFirst();
        int id = c1.getInt(0);
        Cursor c2 = ref_db.rawQuery("SELECT TASK FROM TASKS WHERE userID=?", new String[]{String.valueOf(id)});
        return c2.getCount();
    }
    public void addIngredient(Ingredient ingredient) {
        DatabaseReference reff = firebaseDatabase.getReference().child("Ingredient");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    initValueIngredient = (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ingredient.setId(initValueIngredient+1);


        databaseReference.child("Ingredient").child(String.valueOf(ingredient.getId())).setValue(ingredient);

    }

    public void getIngredient(int id, IngredientDataStatus dataStatus) {
        DatabaseReference reff = firebaseDatabase.getReference().child("Ingredient");
        Query query = reff.orderByKey().equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ingredient ingredient = new Ingredient();
                dataStatus.getIngredient(ingredient);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getAllIngredient(IngredientDataStatus dataStatus ) {


        databaseReference.child("Ingredient").addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Ingredient> ingredients = new ArrayList<Ingredient>();
                for(DataSnapshot ojbSnapshot : snapshot.getChildren())
                {
                    Ingredient ingredient = ojbSnapshot.getValue(Ingredient.class);
                    ingredients.add(ingredient);

                }
                dataStatus.getIngredients(ingredients);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }

    public void addRecipe(Recipe recipe) {
        DatabaseReference reff = firebaseDatabase.getReference().child("Recipe");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    initValueRecipe = (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recipe.setId(initValueRecipe+1);


        databaseReference.child("Recipe").child(String.valueOf(recipe.getId())).setValue(recipe);
        for (int i = 0; i <= recipe.getIngredients().size(); i++) {
            recipe.getIngredients().get(i).setRecipeId(recipe.getId());
            databaseReference.child("RecipeIngredient").child(String.valueOf(recipe.getIngredients().get(i).getRecipeId())).setValue(recipe.getIngredients().get(i));
        }





    }

    public void deleteRecipe(Recipe recipe) {
        databaseReference.child("Recipe").child(String.valueOf(recipe.getId())).removeValue();

    }

    public void getRecipes(User user,RecipeDataStatus dataStatus) {

        DatabaseReference reff = firebaseDatabase.getReference().child("Recipe");
        Query query = reff.orderByChild("user").equalTo(user.getId());
        query.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> recipes = new ArrayList<Recipe>();
                for(DataSnapshot ojbSnapshot : snapshot.getChildren())
                {
                    Recipe recipe = ojbSnapshot.getValue(Recipe.class);
                        recipes.add(recipe);
                }
                dataStatus.getUserRecipe(recipes);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    public void getRecipeIngredient(Recipe recipe,RecipeDataStatus dataStatus) {


        DatabaseReference reff = firebaseDatabase.getReference().child("RecipeIngredient");
        Query query = reff.orderByChild("recipeId").equalTo(recipe.getId());
        query.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<RecipeIngredient> recipeIngredients = new ArrayList<RecipeIngredient>();
                for(DataSnapshot ojbSnapshot : snapshot.getChildren())
                {
                    RecipeIngredient recipeIngredient = ojbSnapshot.getValue(RecipeIngredient.class);
                    recipeIngredients.add(recipeIngredient);
                }
                recipe.addListIngredient(recipeIngredients);
                dataStatus.getRecipeIngredients(recipe);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public Bitmap blobToBitmap(byte[] img) {
        return BitmapFactory.decodeByteArray(img, 0, img.length);
    }

    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    public void getAllRecipes(RecipeDataStatus dataStatus) {
        databaseReference.child("Recipe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> recipes = new ArrayList<Recipe>();
                for(DataSnapshot ojbSnapshot : snapshot.getChildren())
                {
                    Recipe recipe = ojbSnapshot.getValue(Recipe.class);
                    recipes.add(recipe);

                }
                dataStatus.getAllRecipe(recipes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getRecipe(int id,RecipeDataStatus dataStatus)
    {
        DatabaseReference reff = firebaseDatabase.getReference().child("Recipe");
        Query query = reff.orderByKey().equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Recipe recipe = new Recipe();
                dataStatus.getRecipe(recipe);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }*/

}
