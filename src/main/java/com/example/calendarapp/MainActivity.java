package com.example.calendarapp;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;



import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // DANIEL CODE
    private static final String TAG = "Daniel";
    private FirebaseAuth mAuth; // Firebase Authentification *MUST HAVE*
    String current_name;
    String current_id; // This is the ID of the Current User using the App, Student/Teacher
    String current_password; // This is the password

    //Initialize, use to get Subject Object, Homework Object etc..
    ArrayList<Student> students = new ArrayList<>();
    ArrayList<Teacher> teachers = new ArrayList<>();
    ArrayList<Subject> subjects = new ArrayList<>();
    ArrayList<Homework> homeworks = new ArrayList<>();

    private EditText notes;
    private EditText subjectName;

    long maxid = 0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference CalendarDatabase = database.getReference();



    //--------------------------------LI CHANG CODE-------------------------------------------------
    private DatabaseReference mDatabase;
    private ListView schedule;
    private CalendarView oneCalendar;
    private ArrayList<String> mDetails = new ArrayList<>(); // Details of timeslot
    private ArrayList<String> mKeys = new ArrayList<>(); // Timeslot or Task

    //----------------------------END OF LI CHANG CODE----------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        //--------------------------------LI CHANG CODE-------------------------------------------------


        schedule = (ListView) findViewById(R.id.schedule);
        oneCalendar = (CalendarView) findViewById(R.id.oneCalendar);

        oneCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month++;
                String dayOfMonthText = Integer.toString(dayOfMonth);
                if (dayOfMonthText.length() == 1) {
                    dayOfMonthText = "0" + dayOfMonthText;
                }

                String monthText = Integer.toString(month);
                int length2 = String.valueOf(monthText).length();
                if (length2 == 1) {
                    monthText = "0" + monthText;
                }

               // String monthText = Integer.toString(month);
                String yearText = Integer.toString(year);
                String date = dayOfMonthText + monthText + yearText;

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Student")
                        .child(current_name).child("Schedule").child(date);
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            Toast.makeText(getApplicationContext(), "No events", Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(getApplicationContext(), mDatabase.getKey(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                mDetails.clear();
                mKeys.clear();

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                        (MainActivity.this, android.R.layout.simple_list_item_1, mDetails);
                schedule.setAdapter(arrayAdapter);

                mDatabase.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String value = dataSnapshot.getValue(String.class);
                        String key = dataSnapshot.getKey();
                        mDetails.add(key + ": " + value);
                        mKeys.add(key);

                        arrayAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String value = dataSnapshot.getValue(String.class);
                        String key = dataSnapshot.getKey();

                        int index = mKeys.indexOf(key);

                        mDetails.set(index, key + ": " + value);
                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //----------------------------END OF LI CHANG CODE----------------------------------------------

        //----------------------------NAVIGATION BAR--------------------------------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //----------------------------END OF NAVIGATION BAR----------------------------------------------

        // Initialize Firebase Authentification
        mAuth = FirebaseAuth.getInstance();

        //Function to Read The Various Child from CalendarDB
        basicReadWrite();

        //Get User Details , ID and Password passed using intent from LoginPage.Java
        Intent intent = new Intent();
        intent = getIntent();
        current_name = intent.getStringExtra("name");
        current_id = intent.getStringExtra("id");
        current_password = intent.getStringExtra("password");

        Toast toast = Toast.makeText(this, current_name + current_id , Toast.LENGTH_LONG);
        toast.show();
        Log.e(TAG,"id" + current_id);
        Log.e(TAG,"password" + current_password);

        notes = (EditText) findViewById(R.id.editText_notes);
        subjectName = (EditText) findViewById(R.id.editText_subjectname);

        Button btn_Notes = findViewById(R.id.btn_Notes);
        btn_Notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference CalendarDatabase = database.getReference();
                CalendarDatabase.child("Notes").child("Notessss2").setValue("duedateis311111");
                */
                SortedNotes(subjectName.getText().toString(),notes.getText().toString());
            }
        });

//        Button btnStopService = findViewById(R.id.button);
//        btnStopService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                stopService();
//            }
//        });
    }

    //----------------------------NAV BAR FUNCTIONS--------------------------------------------------

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent nextIntent = new Intent(this,MainActivity.class);
            //nextIntent.putExtra("name",current_name);
            startActivity(nextIntent);
        } else if (id == R.id.nav_uploadevent) {
            // ------------------- UPLOAD EVENT -------------------------
            Intent nextIntent = new Intent(MainActivity.this,UploadEvent.class);
            nextIntent.putExtra("name",current_name);
            startActivity(nextIntent);
        } else if (id == R.id.nav_uploadnotes) {
            // ------------------- UPLOAD NOTES -------------------------
            Intent nextIntent = new Intent(MainActivity.this,UploadNotes.class);
            nextIntent.putExtra("name",current_name);
            startActivity(nextIntent);
        } else if (id == R.id.nav_subscribe) {
            // ------------------- Subscribe -------------------------
            Intent nextIntent = new Intent(MainActivity.this,SubscribeActivity.class);
            nextIntent.putExtra("name",current_name);
            startActivity(nextIntent);
        } else if (id == R.id.nav_share) {
            Intent nextIntent = new Intent(MainActivity.this,Scroller.class);
            nextIntent.putExtra("name",current_name);
            startActivity(nextIntent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //-----------------------------------END OF NAV BAR ------------------------------------------

    //Retrieve the Database from Firebase and Store as objects in the APP
    public void basicReadWrite() {
        CalendarDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dataSnapshot.child("Teacher").getChildrenCount();

                System.out.println("*************************************************************");
                System.out.println("*************************************************************");
                //----------------Get all data from firebase object orientedly--------------------

                // dataSnapshot = root of Calendar App
                //   |-dataSnapshot.child("Student") eg. use .getKey() or .getValue()
                //   |-dataSnapshot.child("Teacher")
                //   |-dataSnapshot.child("Subject")
                //      |-dataSnapshot.child("Subject").child("Computer Structures")
                //      |-dataSnapshot.child("Subject").child("Introduction To Algorithms")
                //      |-dataSnapshot.child("Subject").child("Introduction to Information Systems and Programming")
                //          |-dataSnapshot.child("Subject").child("Introduction to Information Systems and Programming").child("Homework")

                // Examples of printing out Data
                System.out.println("Database Children: " + dataSnapshot.getValue());
                System.out.println("Database Children: " + dataSnapshot.child("Student").getValue());



                // GET STUDENTS
                ArrayList<Student> students = new ArrayList<>();
                for (DataSnapshot studentDataSnapshot : dataSnapshot.child("Student").getChildren()) {
                    students.add(studentDataSnapshot.getValue(Student.class));
                }

                // GET TEACHERS
                ArrayList<Teacher> teachers = new ArrayList<>();
                for (DataSnapshot teacherDataSnapshot : dataSnapshot.child("Teacher").getChildren()){
                    teachers.add(teacherDataSnapshot.getValue(Teacher.class));
                }

                // Get SUBJECTS (One of the more confusing Loops)
                ArrayList<Subject> subjects = new ArrayList<>();
                ArrayList<Homework> homeworks = new ArrayList<>();
                // for loop for each subject
                for (DataSnapshot subjectDataSnapshot : dataSnapshot.child("Subject").getChildren()){
                    subjects.add(subjectDataSnapshot.getValue(Subject.class));
                    // for loop for each homework in each subject
                    for(DataSnapshot homeworkDataSnapshot : subjectDataSnapshot.child("Homework").getChildren()) {
                        homeworks.add(homeworkDataSnapshot.getValue(Homework.class));
                    }
                }

                // Print out all Data Respectively, eg. Student Teacher
                //System.out.println("Student_ArrayList: " + Arrays.toString(students.toArray()));
                Log.e(TAG, "Student_ArrayList: " + Arrays.toString(students.toArray()));
                Log.e(TAG, "Teacher_Array_List: " + Arrays.toString(teachers.toArray()));
                Log.e(TAG, "Subject_Array_List: " + Arrays.toString(subjects.toArray()));
                Log.e(TAG, "Homework_Array_List: " + Arrays.toString(homeworks.toArray()));

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*
        CalendarDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.child("Teacher").getChildrenCount();

                System.out.println("*************************************************************");
                System.out.println("*************************************************************");
                //----------------Get all data from firebase object orientedly--------------------

                // dataSnapshot = root of Calendar App
                //   |-dataSnapshot.child("Student") eg. use .getKey() or .getValue()
                //   |-dataSnapshot.child("Teacher")
                //   |-dataSnapshot.child("Subject")
                //      |-dataSnapshot.child("Subject").child("Computer Structures")
                //      |-dataSnapshot.child("Subject").child("Introduction To Algorithms")
                //      |-dataSnapshot.child("Subject").child("Introduction to Information Systems and Programming")
                //          |-dataSnapshot.child("Subject").child("Introduction to Information Systems and Programming").child("Homework")

                // Examples of printing out Data
                System.out.println("Database Children: " + dataSnapshot.getValue());
                System.out.println("Database Children: " + dataSnapshot.child("Student").getValue());



                // GET STUDENTS
                ArrayList<Student> students = new ArrayList<>();
                for (DataSnapshot studentDataSnapshot : dataSnapshot.child("Student").getChildren()) {
                    students.add(studentDataSnapshot.getValue(Student.class));
                }

                // GET TEACHERS
                ArrayList<Teacher> teachers = new ArrayList<>();
                for (DataSnapshot teacherDataSnapshot : dataSnapshot.child("Teacher").getChildren()){
                    teachers.add(teacherDataSnapshot.getValue(Teacher.class));
                }

                // Get SUBJECTS (One of the more confusing Loops)
                ArrayList<Subject> subjects = new ArrayList<>();
                ArrayList<Homework> homeworks = new ArrayList<>();
                // for loop for each subject
                for (DataSnapshot subjectDataSnapshot : dataSnapshot.child("Subject").getChildren()){
                    subjects.add(subjectDataSnapshot.getValue(Subject.class));
                    // for loop for each homework in each subject
                    for(DataSnapshot homeworkDataSnapshot : subjectDataSnapshot.child("Homework").getChildren()) {
                        homeworks.add(homeworkDataSnapshot.getValue(Homework.class));
                    }
                }

                // Print out all Data Respectively, eg. Student Teacher
                //System.out.println("Student_ArrayList: " + Arrays.toString(students.toArray()));
                Log.e(TAG, "Student_ArrayList: " + Arrays.toString(students.toArray()));
                Log.e(TAG, "Teacher_Array_List: " + Arrays.toString(teachers.toArray()));
                Log.e(TAG, "Subject_Array_List: " + Arrays.toString(subjects.toArray()));
                Log.e(TAG, "Homework_Array_List: " + Arrays.toString(homeworks.toArray()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });

        */
    } // basicReadWrite() End

    //Add new functions here
    public void SortedNotes(final String subjectName,final String notes){
        //Insert Notes based on Subject
        CalendarDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maxid = dataSnapshot.child("Subject").child(subjectName).child("Notes").getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        CalendarDatabase.child("Subject").child(subjectName).child("Notes").child(String.valueOf(maxid+1)).setValue(notes);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, MyService.class);
        stopService(serviceIntent);
    }
}// MainActivity End
