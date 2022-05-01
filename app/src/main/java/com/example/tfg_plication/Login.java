package com.example.tfg_plication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.entity.User;

public class Login extends AppCompatActivity {
    private ControllerDB controllerDB;
    private final String FONT_STYLE_SPLASH = "Little Comet Demo Version.otf";
    private Button buttonSign;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        controllerDB = new ControllerDB(this);
        getSupportActionBar().hide();
        buttonSign = (Button) findViewById(R.id.button_sign);

        //changeFont();

        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent changeSign = new Intent (Login.this,MainActivity.class);
              startActivity(changeSign);
            }
        });
    }

    /*private void changeFont() {
        Typeface font = Typeface.createFromAsset(getAssets(), FONT_STYLE_SPLASH);
        TextView loading = (TextView) findViewById(R.id.tittle_log);
        loading.setTypeface(font);
    }
*/
    public void createAccount(View view) {
        Intent intent = new Intent(this, Sign_in.class);
        startActivity(intent);
    }

    public void LogTheAccount(View view) {
        TextView user = (TextView) findViewById(R.id.user_til);
        String user_name = user.getText().toString();
        TextView pass = (TextView) findViewById(R.id.pass_til);
        String pass_user = pass.getText().toString();
        User user1 = new User();
        user1.setName(user.getText().toString());
        user1.setPass(pass.getText().toString());
        if (user1.getName().isEmpty() || user1.getPass().isEmpty()) {
            Toast toast = Toast.makeText(this, "Please don't let boxes empty", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            if (controllerDB.checkIfUserExists(user1) != 0) {
                if (controllerDB.checkIfPassExists(user1) != 0) {
                    Intent intent = new Intent(this, MainActivity.class);
                    //intent.putExtra("USER_I_NEED", (Parcelable) user1);
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
        Intent intent = new Intent(this, Sign_in.class);
        startActivity(intent);
    }


}