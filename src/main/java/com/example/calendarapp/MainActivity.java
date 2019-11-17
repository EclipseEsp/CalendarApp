package com.example.calendarapp;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");
        //myRef.setValue("Hello, World!");
        mDatabase = FirebaseDatabase.getInstance().getReference("Homework");
        mDatabase.setValue(501);
        mDatabase.child("Due_Date").setValue("101");
        //Log.e("Daniel",mDatabase.getParent().getKey());
        mDatabase.child("Due_Date").setValue("102");
    }
}
