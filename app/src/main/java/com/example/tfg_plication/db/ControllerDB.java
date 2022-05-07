package com.example.tfg_plication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.tfg_plication.entity.Ingredient;
import com.example.tfg_plication.entity.Recipe;
import com.example.tfg_plication.entity.RecipeIngredient;
import com.example.tfg_plication.entity.User;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ControllerDB extends SQLiteOpenHelper {

    public ControllerDB(Context context) {
        super(context, "com.damedix.Tfg_application", null, 13);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERS (ID INTEGER PRIMARY KEY AUTOINCREMENT,USER TEXT NOT NULL,PASS TEXT NOT NULL);");
        db.execSQL("CREATE TABLE INGREDIENTS (ID INTEGER PRIMARY KEY AUTOINCREMENT,INGREDIENT TEXT NOT NULL);");
        db.execSQL("CREATE TABLE RECIPES (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INT NOT NULL," +
                "NAME_RECIPE TEXT NOT NULL," +
                "RECIPE_TEXT TEXT NOT NULL ," +
                "FATTEN TEXT ," +
                "TYPEOFFOOD TEXT," +
                "IMAGE BLOB," +
                "FOREIGN KEY (userId) REFERENCES USERS(ID));");
        db.execSQL("CREATE TABLE RECIPES_INGREDIENTS (ID_RECIPE INTEGER NOT NULL," +
                "ID_INGREDIENT INTEGER NOT NULL," +
                "AMOUNT INT NOT NULL," +
                "FOREIGN KEY (ID_RECIPE) REFERENCES RECIPES(ID)," +
                "FOREIGN KEY (ID_INGREDIENT) REFERENCES INGREDIENTS(ID));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS RECIPES");
        db.execSQL("CREATE TABLE RECIPES (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INT NOT NULL," +
                "NAME_RECIPE TEXT NOT NULL," +
                "RECIPE_TEXT TEXT NOT NULL ," +
                "FATTEN TEXT ," +
                "TYPEOFFOOD TEXT," +
                "IMAGE BLOB," +
                "FOREIGN KEY (userId) REFERENCES USERS(ID));");
    }

    public int checkIfUserExists(User user) {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c1 = ref_db.query("USERS", new String[]{"ID"}, "USER=?", new String[]{user.getName()}, null, null, null);
        return c1.getCount();
    }

    public int checkIfPassExists(User user) {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c1 = ref_db.rawQuery("SELECT ID FROM USERS WHERE USER=? AND PASS=?", new String[]{user.getName(), user.getPass()});
        return c1.getCount();
    }


    public User getUser(int id) {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c1 = ref_db.rawQuery("SELECT ID FROM USERS WHERE USER=? ", new String[]{String.valueOf(id)});
        User user = new User();
        user.setId(id);
        user.setName(c1.getString(1));
        user.setPass(c1.getString(2));
        return user;
    }

    public void insertNewUser(User user) {
        ContentValues cv = new ContentValues();
        cv.put("USER", user.getName());
        cv.put("PASS", user.getPass());
        SQLiteDatabase ref_db = this.getWritableDatabase();
        ref_db.insert("USERS", null, cv);
        ref_db.close();
    }

    /*public int getCantRegisters(User user) {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c1 = ref_db.rawQuery("SELECT ID FROM USERS WHERE USER=?", new String[]{user.getName()});
        c1.moveToFirst();
        int id = c1.getInt(0);
        Cursor c2 = ref_db.rawQuery("SELECT TASK FROM TASKS WHERE userID=?", new String[]{String.valueOf(id)});
        return c2.getCount();
    }*/
    public void addIngredient(Ingredient ingredient) {
        SQLiteDatabase ref_db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("INGREDIENT", ingredient.getName());
        ref_db.insert("INGREDIENTS", null, cv);

        ref_db.close();
    }

    public Ingredient getIngredient(int id) {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c2 = ref_db.rawQuery("SELECT * FROM INGREDIENTS WHERE ID = ? ", new String[]{String.valueOf(id)});
        Ingredient ingredient = new Ingredient();
        ingredient.setId(c2.getInt(0));
        ingredient.setName(c2.getString(1));
        return ingredient;
    }

    public List<Ingredient> getAllIngredient() {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c2 = ref_db.rawQuery("SELECT * FROM INGREDIENTS", null);
        int cant_reg = c2.getCount();
        if (cant_reg == 0) {
            ref_db.close();
            return null;
        } else {
            c2.moveToFirst();
            for (int i = 0; i < cant_reg; i++) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(c2.getInt(0));
                ingredient.setName(c2.getString(1));
                ingredients.add(ingredient);
                c2.moveToNext();
            }
            ref_db.close();
            return ingredients;
        }
    }

    public void addRecipe(Recipe recipe) {
        SQLiteDatabase ref_db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userId", recipe.getUser().getId());
        cv.put("NAME_RECIPE", String.valueOf(recipe.getName()));
        cv.put("RECIPE_TEXT", String.valueOf(recipe.getRecipeText()));
        cv.put("FATTEN", String.valueOf(recipe.getFatten()));
        cv.put("TYPEOFFOOD", String.valueOf(recipe.getTypeofFood()));
        cv.put("IMAGE", getBitmapAsByteArray(recipe.getImg()));
        ref_db.insert("RECIPES", null, cv);
        Cursor c2 = ref_db.rawQuery("SELECT ID FROM RECIPES WHERE NAME_RECIPE=?", new String[]{recipe.getName()});
        c2.moveToFirst();
        int id = c2.getInt(0);

        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            ContentValues cv2 = new ContentValues();
            cv2.put("ID_RECIPE", id);
            cv2.put("ID_INGREDIENT", recipe.getIngredients().get(i).getIngredient().getId());
            cv2.put("AMOUNT", recipe.getIngredients().get(i).getAmount());
            ref_db.insert("RECIPES_INGREDIENTS", null, cv2);
            c2.moveToNext();
        }
        ref_db.close();
    }

    public void deleteRecipe(Recipe recipe) {
        SQLiteDatabase ref_db = this.getWritableDatabase();

        ref_db.delete("RECIPES", "RECIPE=? AND userID=?", new String[]{recipe.getName(), String.valueOf(recipe.getId())});
        ref_db.close();
    }

    public List<Recipe> getRecipes(User user) {
        List<Recipe> recipes = new ArrayList<Recipe>();
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c2 = ref_db.rawQuery("SELECT * FROM RECIPES WHERE userId = ?", new String[]{String.valueOf(user.getId())});
        int cant_reg = c2.getCount();
        if (cant_reg == 0) {
            ref_db.close();
            return null;
        } else {
            c2.moveToFirst();

            String[] tasks = new String[cant_reg];
            for (int i = 0; i < cant_reg; i++) {
                Recipe recipe = new Recipe();
                recipe.setId(c2.getInt(0));
                recipe.setUser(user);
                recipe.setName(c2.getString(2));
                recipe.setRecipeText(c2.getString(3));
                recipe.setFatten(c2.getString(4));
                recipe.setImg(blobToBitmap(c2.getBlob(5)));
                recipes.add(recipe);
                c2.moveToNext();
            }
            ref_db.close();
            return recipes;
        }
    }

    /*public ShowRecipe getRecipeIngredient(ShowRecipe recipe) {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c2 = ref_db.rawQuery("SELECT * FROM RECIPES_INGREDIENTS WHERE ID_INGREDIENT = ? ", new String[]{String.valueOf(recipe.getId())});
        int cant_reg = c2.getCount();
        if (cant_reg == 0) {
            ref_db.close();
            return null;
        } else {
            c2.moveToFirst();
            String[] tasks = new String[cant_reg];
            for (int i = 0; i < cant_reg; i++) {
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.setRecipe(recipe);
                recipeIngredient.setIngredient(getIngredient(c2.getInt(1)));
                recipeIngredient.setAmount(c2.getInt(2));
                recipe.addIngredient(recipeIngredient);
                c2.moveToNext();
            }
            ref_db.close();
            return recipe;
        }
    }*/

    public Bitmap blobToBitmap(byte[] img) {
        return BitmapFactory.decodeByteArray(img, 0, img.length);
    }

    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<Recipe>();
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c2 = ref_db.rawQuery("SELECT * FROM RECIPES", null);
        int cant_reg = c2.getCount();
        if (cant_reg == 0) {
            ref_db.close();
            return null;
        } else {
            c2.moveToFirst();
            for (int i = 0; i < cant_reg; i++) {
                Recipe recipe = new Recipe();
                recipe.setId(c2.getInt(0));
                //recipe.setUser(getUser(c2.getInt(1)));
                recipe.setName(c2.getString(2));
                recipe.setRecipeText(c2.getString(3));
                recipe.setFatten(c2.getString(4));
                recipe.setTypeofFood(c2.getString(5));
                recipe.setImg(blobToBitmap(c2.getBlob(6)));
                recipes.add(recipe);
                c2.moveToNext();
            }
            ref_db.close();
            return recipes;
        }
    }

    public void getTestRecipe() {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c2 = ref_db.rawQuery("SELECT * FROM RECIPES", null);
        int cant_reg = c2.getCount();
        if (cant_reg != 0) {
            c2.moveToFirst();
            for (int i = 0; i < cant_reg; i++) {
                Log.v("ControllerDB", "id-->" + c2.getInt(0));
                Log.v("ControllerDB", "UserId" + c2.getInt(1));
                Log.v("ControllerDB", c2.getString(2));
                Log.v("ControllerDB", c2.getString(3));
                Log.v("ControllerDB", c2.getString(4));
                Log.v("ControllerDB", c2.getString(5));
                Log.v("ControllerDB", "Img-->" + blobToBitmap(c2.getBlob(6)));
                c2.moveToNext();
            }
        }
        ref_db.close();
    }

    public void getMoreInfo() {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c2 = ref_db.rawQuery("SELECT * FROM RECIPES_INGREDIENTS", null);
        int cant_reg = c2.getCount();
        if (cant_reg != 0) {
            c2.moveToFirst();
            for (int i = 0; i < cant_reg; i++) {
                Log.v("ControllerDB", "ID_RECIPE -->" + c2.getInt(0));
                Log.v("ControllerDB", "ID_INGREDIENT -->" + c2.getInt(1));
                Log.v("ControllerDB", "ID_AMOUNT -->" + c2.getInt(2));

                c2.moveToNext();
            }
        }
    }

    /*public ShowRecipe getRecipe(int id)
    {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c2 = ref_db.rawQuery("SELECT * FROM RECIPES WHERE id =1", new String[]{String.valueOf(id)});
        int cant_reg = c2.getCount();
        if (cant_reg == 0) {
            ref_db.close();
            return null;
        } else {
            c2.moveToFirst();
            String[] tasks = new String[cant_reg];
                ShowRecipe recipe = new ShowRecipe();
                recipe.setId(c2.getInt(0));
                recipe.setUser(getUser(c2.getInt(1) ));
                recipe.setName(c2.getString(2));
                recipe.setRecipeText(c2.getString(3));
                recipe.setFatten(c2.getString(4));
                recipe.setImg(blobToBitmap(c2.getBlob(5)));
                getRecipeIngredient(recipe);
            ref_db.close();
            return recipe;
    }
    }*/
    public Recipe getRecipe() {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        ArrayList<RecipeIngredient> listIngredients = new ArrayList<>();
        Ingredient ingredient = null;
        Cursor c1 = ref_db.rawQuery("SELECT * FROM RECIPES WHERE id = 1", null);
        int cant_reg = c1.getCount();
        if (cant_reg == 0) {
            ref_db.close();
            return null;
        } else {
            c1.moveToFirst();
            Recipe recipe = new Recipe();
            recipe.setName(c1.getString(2));
            recipe.setRecipeText(c1.getString(3));
            recipe.setFatten(c1.getString(4));
            recipe.setTypeofFood(c1.getString(5));
            recipe.setImg(blobToBitmap(c1.getBlob(6)));
            /*Cursor c2 = ref_db.rawQuery("SELECT ID FROM RECIPES WHERE NAME_RECIPE=?", new String[]{recipe.getName()});
            c2.moveToFirst();
            int id = c2.getInt(0);
            Cursor c3 = ref_db.rawQuery("SELECT ID_INGREDIENT FROM RECIPES_INGREDIENTS WHERE ID_RECIPE=?", new String[]{String.valueOf(id)});
            int val = c3.getCount();
            c3.moveToFirst();
            for (int i = 0; i < val; i++) {
                Cursor c4 = ref_db.rawQuery("SELECT INGREDIENT FROM INGREDIENTS WHERE ID=?", new String[]{String.valueOf(c3.getInt(0))});
                String ingredientName = c4.getString(0);
                ingredient = new Ingredient(c3.getInt(0),ingredientName);
                listIngredients.add(new RecipeIngredient(ingredient, 0));
                c3.moveToNext();
                c4.moveToNext();
            }
            recipe.addListIngredient(listIngredients);*/

            /*c2.moveToFirst();
            for (int i = 0; i < recipe.getIngredients().size(); i++) { //Ingredient Name and Amount
                //recipe.getIngredients().get(i).getIngredient().getId();// 1 // 2  // 3 // 4
                Cursor c3 = ref_db.rawQuery("SELECT ID_ FROM INGREDIENTS WHERE ID=?", new String[]{String.valueOf(recipe.getIngredients().get(i).getIngredient().getId())});
                c3.moveToFirst();
                String ingredientName = c3.getString(0);
                recipe.getIngredients().get(i).getAmount();
                ingredient = new Ingredient(recipe.getIngredients().get(i).getIngredient().getId(),ingredientName);
                listIngredients.add(new RecipeIngredient(ingredient,recipe.getIngredients().get(i).getAmount()));
                c3.moveToNext();
            }
            ;*/
            ref_db.close();
            return recipe;
        }
    }

}
