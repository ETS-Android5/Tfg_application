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
import com.example.tfg_plication.db.ControllerFB;
import com.example.tfg_plication.entity.User;

public class Login extends AppCompatActivity {
    private ControllerDB controllerDB;
    private final String FONT_STYLE_SPLASH = "StreetExplorer.otf";
    private Button buttonSign;
    private Button buttonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        controllerDB = new ControllerDB(this);
        //controllerDB.getTestRecipe();
        //controllerFB.getMoreInfo();
        controllerDB.testAllIds();

        getSupportActionBar().hide();
        buttonSign = (Button) findViewById(R.id.button_sign);
        buttonLogin = (Button) findViewById(R.id.login_button);

        changeFont();

        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeSign = new Intent(Login.this, Sign_in.class);
                startActivity(changeSign);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView user = (TextView) findViewById(R.id.user_til);
                String user_name = user.getText().toString();
                TextView pass = (TextView) findViewById(R.id.pass_til);
                String pass_user = pass.getText().toString();
                User user1 = new User();
                user1.setName(user.getText().toString());
                user1.setPass(pass.getText().toString());
                if (user1.getName().isEmpty() || user1.getPass().isEmpty()) {
                    Toast toast = Toast.makeText(Login.this, "Please don't let boxes empty", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                    if (controllerDB.checkIfUserExists(user1)!=0) {
                        if (controllerDB.checkIfPassExists(user1) != 0) {
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("idUser",controllerDB.returnIdUser(user1));
                            startActivity(intent);
                        } else {
                            Toast toast = Toast.makeText(Login.this, "Invalid password", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(Login.this, "Invalid user", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
    }

                   /* controllerFB.checkIfUserExists(user1, new ControllerFB.UserDataStatus() {
                        @Override
                        public void onUserExist() {
                            controllerFB.checkIfPassExists(user1, new ControllerFB.PassDataStatus() {
                                @Override
                                public void onPassExist(User user2) {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("idUser", user2.getId());
                                    startActivity(intent);
                                }

                                @Override
                                public void onPassDoesntExist() {
                                    Toast toast = Toast.makeText(Login.this, "Invalid password", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });

                        }

                        @Override
                        public void onUserDoesntExist() {
                            Toast toast = Toast.makeText(Login.this, "Invalid user", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        @Override
                        public void onUserCreated(User user) {

                        }

                        @Override
                        public void onUserGetUser(Long id) {

                        }
                    });
*/





     /*buttonLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView user = (TextView) findViewById(R.id.user_til);
            String user_name = user.getText().toString();
            TextView pass = (TextView) findViewById(R.id.pass_til);
            String pass_user = pass.getText().toString();
            User user1 = new User();
            user1.setName(user.getText().toString());
            user1.setPass(pass.getText().toString());
            if (user1.getName().isEmpty() || user1.getPass().isEmpty()) {
                Toast toast = Toast.makeText(Login.this, "Please don't let boxes empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                controllerFB.checkIfUserExists(user1, new ControllerFB.UserDataStatus() {
                    @Override
                    public void onUserExist() {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("idUser",user1.getId());
                        startActivity(intent);
                    }

                    @Override
                    public void onUserDoesntExist() {
                        Toast toast = Toast.makeText(Login.this, "Invalid user", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });




                    if (controllerFB.checkIfUserExists(user1,this) ) {
                        if (controllerFB.checkIfPassExists(user1) != 0) {
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("idUser",user1.getId());
                            startActivity(intent);
                        } else {
                            Toast toast = Toast.makeText(Login.this, "Invalid password", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(Login.this, "Invalid user", Toast.LENGTH_SHORT);
                        toast.show();
                    }*/




    private void changeFont() {
        Typeface font = Typeface.createFromAsset(getAssets(), FONT_STYLE_SPLASH);
        TextView loading = (TextView) findViewById(R.id.tittle);
        loading.setTypeface(font);
    }
}







