package com.example.ourbank.Firebase;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguraçãoFirebase {
    private static FirebaseAuth firebaseAuth;

    public static FirebaseAuth getFirebaseAuth (){
        if ( firebaseAuth == null ){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

}


