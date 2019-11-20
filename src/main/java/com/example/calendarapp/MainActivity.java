package com.example.calendarapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Daniel";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");
        //myRef.setValue("Hello, World!");
        //mDatabase = FirebaseDatabase.getInstance().getReference("Subject");
        //mDatabase.setValue(501);
        //mDatabase.child("Computer Structures").child("Subscriber").child("test").setValue("101");
        //System.out.println(mDatabase.child("Computer Structures").child("Homework");
        //Log.e("Daniel",mDatabase.child("Computer Structures").child("Homework").orderByChild()
       // mDatabase.child("Due_Date").setValue("10z");

        basicReadWrite();

    }

    public void basicReadWrite() {
        // [START write_message]
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Student");

        //myRef.setValue("Hello, World!");
        // [END write_message]

        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //String value = dataSnapshot.getValue(String.class);
                //List<String> td = (ArrayList<String>) dataSnapshot.getChildren();
                //Log.d(TAG, "Value is: " + value);
                //Log.d(TAG, "Value is: " + td);
                for(DataSnapshot childDataSnapshot: dataSnapshot.child("Joey").getChildren()){
                    Log.v(TAG,""+ childDataSnapshot.getKey()); //displays the key for the node
                    Log.v(TAG,""+ childDataSnapshot.getValue());   //gives the value for given keyname

                    }
                }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // [END read_message]
    }


}
