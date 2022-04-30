package com.example.tfg_plication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg_plication.db.ControllerDB;

public class Login extends AppCompatActivity {
    private ControllerDB controllerDB;
    private final String FONT_STYLE_SPLASH = "Little Comet Demo Version.otf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        controllerDB = new ControllerDB(this);
        getSupportActionBar().hide();
        //changeFont();
    }

    /*private void changeFont() {
        Typeface font = Typeface.createFromAsset(getAssets(), FONT_STYLE_SPLASH);
        TextView loading = (TextView) findViewById(R.id.tittle_log);
        loading.setTypeface(font);
    }

    public void createAccount(View view) {
        //Intent intent = new Intent(this, SignUp.class);
        //startActivity(intent);
    }

    public void LogTheAccount(View view) {
        TextView user = (TextView) findViewById(R.id.user_til);
        String user_name = user.getText().toString();
        TextView pass = (TextView) findViewById(R.id.pass_til);
        String pass_user = pass.getText().toString();
        if (user_name.isEmpty() || pass_user.isEmpty()) {
            Toast toast = Toast.makeText(this, "Please don't let boxes empty", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            if (controllerDB.checkIfUserExists(user_name) != 0) {
                if (controllerDB.checkIfPassExists(user_name, pass_user) != 0) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("USER_I_NEED", user_name);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(this, "Invalid user", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void moveToSignUp(View view) {
        //Intent intent = new Intent(this, SignUser.class);
        //startActivity(intent);
    }*/
}