package com.example.tfg_plication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ControllerDB extends SQLiteOpenHelper{

    public ControllerDB(Context context){
        super(context, "com.damedix.Tfg_application",null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERS (ID INTEGER PRIMARY KEY AUTOINCREMENT,USER TEXT NOT NULL,PASS TEXT NOT NULL);");
        db.execSQL("CREATE TABLE INGREDIENTS (ID INTEGER PRIMARY KEY AUTOINCREMENT,INGREDIENT TEXT NOT NULL,PASS TEXT NOT NULL);");
        db.execSQL("CREATE TABLE RECIPES (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME_RECIPE TEXT NOT NULL," +
                "AMOUNT TEXT NOT NULL,"+
                "FATTEN TEXT NOT NULL,"+
                "ID_USER INTEGER NOT NULL," +
                "ID_INGREDIENT INTEGER NOT NULL,"+
                "FOREIGN KEY (userID) REFERENCES USERS(ID));");
        db.execSQL(CREATE TABLE RECIPES_INGREDIENTS ();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int checkIfUserExists(String user) {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c1 = ref_db.query("USERS", new String[]{"ID"}, "USER=?", new String[]{user}, null, null, null);
        return c1.getCount();
    }
    public int checkIfPassExists(String user,String pass) {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c1 = ref_db.rawQuery("SELECT ID FROM USERS WHERE USER=? AND PASS=?", new String[]{user,pass});
        return c1.getCount();
    }

    public void insertNewUser(String user, String password) {
        ContentValues cv = new ContentValues();
        cv.put("USER", user);
        cv.put("PASS", password);
        SQLiteDatabase ref_db = this.getWritableDatabase();
        ref_db.insert("USERS", null, cv);
        ref_db.close();
    }

    public void addTask(String user, String task) {
        SQLiteDatabase ref_db = this.getWritableDatabase();

        Cursor c1 = ref_db.rawQuery("SELECT ID FROM USERS WHERE USER=?", new String[]{user});
        c1.moveToFirst();
        int id = c1.getInt(0);

        ContentValues cv = new ContentValues();
        cv.put("userID", String.valueOf(id));//horse - 1234 / adri - 1234
        cv.put("TASK", task);

        ref_db.insert("TASKS", null, cv);
        //Log.v("ControllerDB", "User ID: "+id+" - "+"task: "+task);
        ref_db.close();
    }

    public  String [] getAllTasks(String user){
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c1 = ref_db.rawQuery("SELECT ID FROM USERS WHERE USER=?", new String[]{user});
        c1.moveToFirst();
        int id = c1.getInt(0);
        Cursor c2 = ref_db.rawQuery("SELECT * FROM TASKS WHERE userID=?", new String[]{String.valueOf(id)});
        int cant_reg = c2.getCount();
        if (cant_reg == 0) {
            ref_db.close();
            return null;
        } else {
            c2.moveToFirst();
            String[] tasks = new String[cant_reg];
            for (int i = 0; i < cant_reg; i++) {
                tasks[i] = c2.getString(1);
                c2.moveToNext();
            }
            ref_db.close();
            return tasks;
        }
    }

    public int getCantRegisters(String user) {
        SQLiteDatabase ref_db = this.getReadableDatabase();
        Cursor c1 = ref_db.rawQuery("SELECT ID FROM USERS WHERE USER=?", new String[]{user});
        c1.moveToFirst();
        int id = c1.getInt(0);
        Cursor c2 = ref_db.rawQuery("SELECT TASK FROM TASKS WHERE userID=?", new String[]{String.valueOf(id)});
        return c2.getCount();
    }

    public void doneTask(String user, String task) {
        SQLiteDatabase ref_db = this.getWritableDatabase();
        Cursor c1 = ref_db.rawQuery("SELECT ID FROM USERS WHERE USER=?", new String[]{user});
        c1.moveToFirst();
        int id = c1.getInt(0);
        ref_db.delete("TASKS", "TASK=? AND userID=?",new String[]{task,String.valueOf(id)});
        ref_db.close();
    }
}
