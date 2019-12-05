package com.example.calendarapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Uploading Sorted Notes into respective tree in CalendarDB
//THIS IS NEVER USED
public class SortedNotes{
    public void upload(String tag, String notes){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference CalendarDatabase = database.getReference();
        CalendarDatabase.child("Subject").child("Notes").child("Note1").setValue(notes);
    }
}
