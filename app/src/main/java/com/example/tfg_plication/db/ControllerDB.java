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

public class ControllerDB extends SQLiteOpenHelper{

    public ControllerDB(Context context){
        super(context, "com.damedix.Tfg_application",null,6);

    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERS (ID INTEGER PRIMARY KEY AUTOINCREMENT,USER TEXT NOT NULL,PASS TEXT NOT NULL);");
        db.execSQL("CREATE TABLE INGREDIENTS (ID INTEGER PRIMARY KEY AUTOINCREMENT,INGREDIENT TEXT NOT NULL);");
        db.execSQL("CREATE TABLE RECIPES (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INT NOT NULL,"+
                "NAME_RECIPE TEXT NOT NULL," +
                "RECIPE_TEXT TEXT NOT NULL ,"+
                "FATTEN TEXT ,"+
                "TYPEOFFOOD TEXT,"+
                "IMAGE BLOB,"+
                "FOREIGN KEY (userID) REFERENCES USERS(ID));");
        db.execSQL("CREATE TABLE RECIPES_INGREDIENTS (ID_RECIPE INTEGER NOT NULL," +
                "ID_INGREDIENT INTEGER NOT NULL,"+
                "AMOUNT INT NOT NULL," +
                "FOREIGN KEY (ID_RECIPE) REFERENCES RECIPES(ID)," +
                "FOREIGN KEY (ID_INGREDIENT) REFERENCES INGREDIENTS(ID));");

        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Tomate')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Pollo')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Harina')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Huevo')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Leche')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Queso Untable')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Lechuga')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Zanahoria')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Limon')");

        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('levadura')");

        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Levadura')");

        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Sal')");
        db.execSQL("INSERT INTO INGREDIENTS (INGREDIENT) VALUES ('Coliflor')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int checkIfUserExists(User user) {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c1 = ref_db.query("USERS", new String[]{"ID"}, "USER=?", new String[]{user.getName()}, null, null, null);
        return c1.getCount();
    }
    public int checkIfPassExists(User user) {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c1 = ref_db.rawQuery("SELECT ID FROM USERS WHERE USER=? AND PASS=?", new String[]{user.getName(),user.getPass()});
        return c1.getCount();
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
    public Ingredient getIngredient(int id){
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c2 = ref_db.rawQuery("SELECT * FROM INGREDIENTS WHERE ID = ? ", new String[]{String.valueOf(id)});
        Ingredient ingredient = new Ingredient();
        ingredient.setId(c2.getInt(0));
        ingredient.setName(c2.getString(1));
        return ingredient;
    }
    public List<Ingredient> getAllIngredient(){
        List<Ingredient> ingredients=new ArrayList<Ingredient>();
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c2 = ref_db.rawQuery("SELECT * FROM INGREDIENTS",null);
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
    public void addRecipe( Recipe recipe) {
        SQLiteDatabase ref_db = this.getWritableDatabase();


        ContentValues cv = new ContentValues();
        cv.put("NAME_RECIPE", String.valueOf(recipe.getName()));
        cv.put("RECIPE_TEXT", String.valueOf(recipe.getRecipeText()));
        cv.put("FATTEN", String.valueOf(recipe.getFatten()));
        cv.put("TYPEOFFOOD", String.valueOf(recipe.getFatten()));
        cv.put("IMAGE",getBitmapAsByteArray(recipe.getImg()));
        ref_db.insert("RECIPES", null, cv);
        Cursor c2 = ref_db.rawQuery("SELECT ID FROM INGREDIENTS WHERE NAME_RECIPE=?", new String[]{recipe.getName()});
        c2.moveToFirst();
        int idRecipe = c2.getInt(0);
        for(int i=0;i<=recipe.getIngredients().size();i++){
            ContentValues cv2 = new ContentValues();
            cv2.put("ID_RECIPE", Integer.valueOf(recipe.getId()));
            cv2.put("ID_INGREDIENT", Integer.valueOf(recipe.getIngredients().get(i).getIngredient().getId()));
            cv2.put("AMOUNT",Integer.valueOf(recipe.getIngredients().get(i).getAmount()));
            ref_db.insert("RECIPES_INGREDIENTS",null,cv2);
        }
        ref_db.close();
    }
    public void deleteRecipe(Recipe recipe) {
        SQLiteDatabase ref_db = this.getWritableDatabase();

        ref_db.delete("RECIPES", "RECIPE=? AND userID=?",new String[]{recipe.getName(),String.valueOf(recipe.getId())});
        ref_db.close();
    }
    public List<Recipe> getRecipes(User user){
        List<Recipe> recipes= new ArrayList<Recipe>();
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

            }
            ref_db.close();
            return recipes;
        }
    }
    public Recipe getRecipeIngredient(Recipe recipe){
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
            }
            ref_db.close();
            return recipe;
        }
    }
    
    public Bitmap blobToBitmap(byte[] img){


        return BitmapFactory.decodeByteArray(img, 0, img.length);  }

    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


}
