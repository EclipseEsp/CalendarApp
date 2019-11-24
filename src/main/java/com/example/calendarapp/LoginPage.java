package com.example.calendarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginPage extends AppCompatActivity {

    private static final String TAG = "Daniel";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    ArrayList<Student> students = new ArrayList<>();
    ArrayList<Teacher> teachers = new ArrayList<>();

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        basicReadWrite();
        Name=(EditText)findViewById(R.id.edtxtName);
        Password=(EditText)findViewById(R.id.edtxtPassword);
        Info=(TextView)findViewById(R.id.txtviewinfo);
        Login=(Button)findViewById(R.id.btnLogin);

        Info.setText("Number of attempts remaining: 5");

        ArrayList<Integer> s_ids = new ArrayList<>();
        ArrayList<String> s_passwords = new ArrayList<>();
        ArrayList<Integer> t_ids = new ArrayList<>();
        ArrayList<String> t_passwords = new ArrayList<>();

        /*
        for(Student s: students){
            s_ids.add(s.id);
            s_passwords.add(s.password);
        }
        for(Teacher t: teachers){
            t_ids.add(t.id);
            t_passwords.add(t.password);
        }
        */

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    private void validate(String userName,String userPassword){
        for(Student s: students){
            if(s.id == Integer.valueOf(userName) && s.password.equals(userPassword)){
                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                startActivity(intent);
                break;
            }
        }

        for(Teacher t: teachers){
            if(t.id == Integer.valueOf(userName) && t.password.equals(userPassword)){
                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                startActivity(intent);
                break;
            }
        }
        /*
        if(userName.equals("1234") && (userPassword.equals("1234"))) {
            Intent intent = new Intent(LoginPage.this, MainActivity.class);
            startActivity(intent);
            */

        /*
        }else{
            counter--;

            Info.setText("Number of attempts remaining: "+ String.valueOf(counter));

            if(counter==0){
                Login.setEnabled(false);
            }
        }
        */
    }

    // Retreive Data from database
    public void basicReadWrite() {

        // [START write_message]
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //final DatabaseReference StudentDatabase = database.getReference("Student");
        final DatabaseReference CalendarDatabase = database.getReference();

        //final ArrayList<Student> students = new ArrayList<>();
        //final ArrayList<Teacher> teachers = new ArrayList<>();

        //myRef.setValue("Hello, World!");
        // [END write_message]

        // [START read_message]
        // Read from the database

        CalendarDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Example of getting a particular Student Object
                //Student st udent = dataSnapshot.child("Joey").getValue(Student.class);
                //System.out.println(dataSnapshot.getKey() + " id is " + student.id);

                /* Get each detail in Joey
                for (DataSnapshot childDataSnapshot : dataSnapshot.child("Joey").getChildren()) {
                    Log.v(TAG, "" + childDataSnapshot.getKey()); //displays the key for the node
                    Log.v(TAG, "" + childDataSnapshot.getValue());   //gives the value for given keyname

                }
                */

                System.out.println("*************************************************************");
                System.out.println("*************************************************************");
                //----------------Get all data from firebase object orientedly--------------------


                // Examples of printing out Data// dataSnapshot = root of Calendar App
                //                //   |-dataSnapshot.child("Student") eg. use .getKey() or .getValue()
                //                //   |-dataSnapshot.child("Teacher")
                //                //   |-dataSnapshot.child("Subject")
                //                //      |-dataSnapshot.child("Subject").child("Computer Structures")
                //                //      |-dataSnapshot.child("Subject").child("Introduction To Algorithms")
                //                //      |-dataSnapshot.child("Subject").child("Introduction to Information Systems and Programming")
                //                //          |-dataSnapshot.child("Subject").child("Introduction to Information Systems and Programming").child("Homework")
                System.out.println("Database Children: " + dataSnapshot.getValue());
                System.out.println("Database Children: " + dataSnapshot.child("Student").getValue());


                // GET STUDENTS
                for (DataSnapshot studentDataSnapshot : dataSnapshot.child("Student").getChildren()) {
                    students.add(studentDataSnapshot.getValue(Student.class));
                }

                // GET TEACHERS
                for (DataSnapshot teacherDataSnapshot : dataSnapshot.child("Teacher").getChildren()){
                    teachers.add(teacherDataSnapshot.getValue(Teacher.class));
                }

                // Print out all Data Respectively, eg. Student Teacher
                //System.out.println("Student_ArrayList: " + Arrays.toString(students.toArray()));
                Log.d(TAG, "Student_ArrayList: " + Arrays.toString(students.toArray()));
                //System.out.println("Teacher_Array_List: " + Arrays.toString(teachers.toArray()));
                Log.d(TAG, "Teacher_Array_List: " + Arrays.toString(teachers.toArray()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        /*
        ArrayList<String> ids = new ArrayList();
        ArrayList<String> passwords = new ArrayList();
        for(Student s:students){
            ids.add(s.id);
            passwords.add(s.password);
        }
        for(Teacher t:teachers){
            ids.add(t.id);
            passwords.add(t.password);
        }
        */

    } // basicReadWrite() End
}
