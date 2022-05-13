package com.example.tfg_plication;

import android.content.Intent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.db.ControllerFB;
import com.example.tfg_plication.entity.Ingredient;
import com.example.tfg_plication.entity.Recipe;
import com.example.tfg_plication.entity.RecipeIngredient;
import com.example.tfg_plication.entity.User;

import java.util.ArrayList;
import java.util.List;

public class Sign_in extends AppCompatActivity {
    ControllerDB controllerDB;
    private Button buttonCheck_in;
    EditText boxUser;
    EditText pass;
    private Button buttonBack;
    private final String FONT_STYLE_SPLASH = "StreetExplorer.otf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        changeFont();


        buttonCheck_in = findViewById(R.id.buttonCheck_in);
        controllerDB = new ControllerDB(this);
        buttonBack = findViewById(R.id.buttonBack);

        buttonCheck_in.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              boxUser = (EditText) findViewById(R.id.boxUser);

                              pass = (EditText) findViewById(R.id.boxPass);
                              User user = new User(boxUser, pass);

                              user.setName(boxUser.getText().toString());
                              user.setPass(pass.getText().toString());
                              if(user.getName().toString().length()!=0) {
                                  if(user.getPass().toString().length()!=0){
                                     if (controllerDB.checkIfUserExists(user) > 0) {
                                      Toast userExist = Toast.makeText(Sign_in.this, "This username already exists", Toast.LENGTH_LONG);
                                      userExist.show();
                                    } else {
                                      Toast userCreate = Toast.makeText(Sign_in.this, "User Create", Toast.LENGTH_LONG);
                                      userCreate.show();
                                      controllerDB.insertNewUser(user);
                                      Intent intent = new Intent(Sign_in.this, MainActivity.class);
                                      intent.putExtra("idUser", user.getId());
                                      startActivity(intent);
                                  }
                              }else{
                                  Toast passEmpty = Toast.makeText(Sign_in.this, "Pass cannot be empty", Toast.LENGTH_LONG);
                                  passEmpty.show();
                              }
                          }else{
                              Toast userEmpty = Toast.makeText(Sign_in.this, "User cannot be empty", Toast.LENGTH_LONG);
                               userEmpty.show();
                          }









                           /*  if (user.getName().toString().length() != 0) {
                                  if (user.getPass().toString().length() != 0) {



                                      Ingredient ingrediente = new Ingredient();
                                      ingrediente.setName("Curry");
                                      RecipeIngredient ingrediente1 = new RecipeIngredient();
                                      ingrediente1.setIngredient(ingrediente);
                                      ingrediente1.setAmount(2000);
                                      RecipeIngredient ingrediente2 = new RecipeIngredient();
                                      ingrediente1.setIngredient(ingrediente);
                                      ingrediente1.setAmount(3000);
                                      List<RecipeIngredient> ingredientes = new ArrayList<RecipeIngredient>();
                                      ingredientes.add(ingrediente1);
                                      ingredientes.add(ingrediente2);
                                      Recipe receta = new Recipe();
                                      receta.setUser(user);
                                      receta.setRecipeText("En un lugar de la mancha");
                                      receta.setName("Pisto");
                                      receta.setFatten("15000");
                                      receta.addListIngredient(ingredientes);
                                      controllerFB.addRecipe(receta, new ControllerFB.CreateDataStatus() {
                                          @Override
                                          public void OnCreateIngredient(Ingredient ingredient) {

                                          }

                                          @Override
                                          public void OnCreateRecipe(Recipe recipe) {
                                              controllerFB.addIngredient(ingrediente, new ControllerFB.CreateDataStatus() {
                                                  @Override
                                                  public void OnCreateIngredient(Ingredient ingredient) {

                                                  }

                                                  @Override
                                                  public void OnCreateRecipe(Recipe recipe) {

                                                  }
                                              });
                                          }
                                      });





                                      controllerFB.checkIfUserExists(user, new ControllerFB.UserDataStatus() {
                                          @Override
                                          public void onUserExist() {
                                              Toast toast = Toast.makeText(Sign_in.this, "Invalid, user Exist", Toast.LENGTH_SHORT);
                                              toast.show();

                                          }

                                          @Override
                                          public void onUserDoesntExist() {
                                              controllerFB.insertNewUser(user, new ControllerFB.UserDataStatus() {
                                                  @Override
                                                  public void onUserExist() {

                                                  }

                                                  @Override
                                                  public void onUserDoesntExist() {

                                                  }

                                                  @Override
                                                  public void onUserCreated(User user) {


                                                      Intent intent = new Intent(Sign_in.this, MainActivity.class);
                                                      intent.putExtra("idUser", user.getId());
                                                      startActivity(intent);








                                                  }

                                                  @Override
                                                  public void onUserGetUser(Long id) {

                                                  }
                                              });

                                          }
                                          @Override
                                          public void onUserCreated(User user) {

                                          }

                                          @Override
                                          public void onUserGetUser(Long id) {

                                          }

                                      });
                                  }
                              }*/
                          }


                       /* if(controllerDB.checkIfUserExists(user)){
                          Toast userExist = Toast.makeText(Sign_in.this, "This username already exists", Toast.LENGTH_LONG);
                            userExist.show();
                        }else{

                            Toast userCreate = Toast.makeText(Sign_in.this, "User Create", Toast.LENGTH_LONG);
                            userCreate.show();
                            controllerDB.insertNewUser
                            Intent intent = new Intent(Sign_in.this,MainActivity.class);
                            intent.putExtra("idUser",user.getId());
                            startActivity(intent);
                        }
                    }else{
                        Toast passEmpty = Toast.makeText(Sign_in.this, "Pass cannot be empty", Toast.LENGTH_LONG);
                        passEmpty.show();
                    }
                }else{
                    Toast userEmpty = Toast.makeText(Sign_in.this, "User cannot be empty", Toast.LENGTH_LONG);
                    userEmpty.show();
                }*/


                                              });

                buttonBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentBack = new Intent(Sign_in.this, Login.class);
                        startActivity(intentBack);
                    }
                    });


    }

    private void changeFont() {
        Typeface font = Typeface.createFromAsset(getAssets(), FONT_STYLE_SPLASH);
        TextView loading = (TextView) findViewById(R.id.tittle);
        loading.setTypeface(font);
    }
    }
