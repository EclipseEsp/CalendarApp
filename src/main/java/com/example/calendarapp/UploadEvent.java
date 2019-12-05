package com.example.calendarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class UploadEvent extends AppCompatActivity {
    private String TAG = "DANIEL";
    private Button fireBaseBtn;
    private EditText ddmmyyyy;
    private EditText timeOrTask;
    private EditText details;
    private DatabaseReference databaseReference; //Firebase data ref

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    String datetrimmed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_event);

        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        UploadEvent.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = day + "/" + month + "/" + year;
                datetrimmed = day + "" + month + "" + year;
                mDisplayDate.setText(date);
            }
        };


        Intent newIntent = getIntent();
        final String current_name = newIntent.getStringExtra("name");

        Toast toast = Toast.makeText(this, current_name , Toast.LENGTH_LONG);
        toast.show();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        ddmmyyyy = (EditText) findViewById(R.id.dateText);
        timeOrTask = (EditText) findViewById(R.id.timeTaskText);
        details = (EditText) findViewById(R.id.detailsText);

        fireBaseBtn = (Button) findViewById(R.id.fireBaseBtn);
        fireBaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a child in root obj and assign a value to a child
                //String ddmmyyyyText = ddmmyyyy.getText().toString().trim();
                String ddmmyyyyText = datetrimmed;
                String timeOrTaskText = timeOrTask.getText().toString().trim();
                String detailsText = details.getText().toString().trim();

                databaseReference.child("Student").child(current_name).child("Schedule").child(ddmmyyyyText)
                        .child(timeOrTaskText).setValue(detailsText).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UploadEvent.this, "Stored...", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(UploadEvent.this, "ERROR!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
