package com.example.hezeyan.myapplication;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtil {

    private static final String URL = "https://touristurcoapp.firebaseio.com";
    private static FirebaseDatabase DB_INSTANCE;

    public static FirebaseDatabase getDBInstance() {
        if (DB_INSTANCE == null) {
            DB_INSTANCE = FirebaseDatabase.getInstance(URL);
        }

        return DB_INSTANCE;
    }
}
