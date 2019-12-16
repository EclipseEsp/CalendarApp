package com.example.calendarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Scroller extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference CalendarDatabase = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);

        Intent newIntent = getIntent();
        final String current_name = newIntent.getStringExtra("name");
        final String date = new String();
        final String task = new String();

        //ScrollView scroll = findViewById(R.id.scrollView);
        final LinearLayout layout = findViewById(R.id.linear_scroll);


        DatabaseReference fromPath = CalendarDatabase.child("Student")
                .child(current_name).child("Schedule");
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot copy : dataSnapshot.getChildren()) {
                    String Date = copy.getKey();
                    //String Value = copy.getValue(String.class);
                    //toPath.child(Key).setValue(Value);
                    for (DataSnapshot Time : copy.getChildren()){
                        String KeyKey = Time.getKey();
                        String ValueValue = Time.getValue(String.class);
                        TextView tv1 = new TextView(Scroller.this);
                        tv1.setTextColor(Color.parseColor("#0C0808"));
                        tv1.setText(Date);
                        tv1.setTextSize(16);
                        tv1.setPadding(5,10,5,0);
                        TextView tv2 = new TextView(Scroller.this);
                        tv2.setText(KeyKey + ": " + ValueValue);
                        tv2.setPadding(5,10,5,30);
                        tv2.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                        layout.addView(tv1);
                        layout.addView(tv2);

//                        TextView tv3 = new TextView(Scroller.this);
//                        SpannableString spannableStringObject= new SpannableString("");
//                        spannableStringObject.setSpan(new UnderlineSpan(), 0, spannableStringObject.length(), 0);
//                        tv3.setText(spannableStringObject);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        TextView tv1 = new TextView(this);
//        tv1.setText("This is tv1");
//        layout.addView(tv1);
//        TextView tv2 = new TextView()
    }
}
