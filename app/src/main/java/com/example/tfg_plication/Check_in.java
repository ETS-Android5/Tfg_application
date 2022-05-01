package com.example.tfg_plication;

import android.content.Intent;

import android.graphics.Typeface;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.entity.User;
public class Check_in extends AppCompatActivity {
    ControllerDB controllerDB;
    private Button buttonCheck_in;
    EditText boxUser;
    EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);



        buttonCheck_in = (Button) findViewById(R.id.buttonCheck_in);
        controllerDB = new ControllerDB(this);

        buttonCheck_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boxUser = (EditText) findViewById(R.id.boxUser);

                pass = (EditText) findViewById(R.id.boxPass);
                User user = new User(boxUser,pass);
                user.setName(boxUser.getText().toString());
                user.setPass(pass.getText().toString());
                if(user.getName().toString().length()!=0){
                    if(user.getPass().toString().length()!=0){

                        if(controllerDB.checkIfUserExists(user)>0){
                            Toast userExist = Toast.makeText(Check_in.this, "Ya existe este usuario", Toast.LENGTH_LONG);
                            userExist.show();
                        }else{
                            controllerDB.insertNewUser(user);
                            Toast userCreate = Toast.makeText(Check_in.this, "User Create", Toast.LENGTH_LONG);
                            userCreate.show();
                            Intent intent = new Intent(Check_in.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }else{
                        Toast passEmpty = Toast.makeText(Check_in.this, "Pass cannot be empty", Toast.LENGTH_LONG);
                        passEmpty.show();
                    }
                }else{
                    Toast userEmpty = Toast.makeText(Check_in.this, "User cannot be empty", Toast.LENGTH_LONG);
                    userEmpty.show();
                }
            }
        });
    }
}