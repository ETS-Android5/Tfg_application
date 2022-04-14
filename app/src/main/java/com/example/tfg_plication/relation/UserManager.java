package com.example.tfg_plication.relation;

import android.content.Context;
import com.example.tfg_plication.db.ControllerDB;
import com.example.tfg_plication.entity.User;

public class UserManager {
    private ControllerDB controllerDB;
    public boolean LogTheAccount(User user, Context context){
        controllerDB = new ControllerDB( context);
        boolean exit=false;

            if(controllerDB.checkIfPassExists(user)!=0){
                exit= true;
            }

        return exit;
    }
    public boolean addNewUser(User user,Context context){
        controllerDB = new ControllerDB( context);
        boolean exit=false;
        if(controllerDB.checkIfPassExists(user)!=0){
            exit= false;
        }else{
            controllerDB.insertNewUser(user);
            exit = true;
        }
        return exit;
    }
}
