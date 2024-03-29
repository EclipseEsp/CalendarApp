package com.example.calendarapp;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

//Miscellaneous Code, Don't Delete !!!!!!!!!!!!!!!!!!!!

public class Misc {
    String TAG = "Daniel";
    public void basicReadWrite() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference CalendarDatabase = database.getReference();

        CalendarDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //List<String> LIST = (ArrayList<String>) dataSnapshot.getChildren();


                // Garbage Examples
                //DatabaseReference myRef = database.getReference("message");
                //myRef.setValue("Hello, World!");
                //mDatabase = FirebaseDatabase.getInstance().getReference("Subject");
                //mDatabase.setValue(501);
                //mDatabase.child("Computer Structures").child("Subscriber").child("test").setValue("101");
                //System.out.println(mDatabase.child("Computer Structures").child("Homework");
                //Log.e("Daniel",mDatabase.child("Computer Structures").child("Homework").orderByChild()
                // mDatabase.child("Due_Date").setValue("10z");

                // Example of getting a particular Student Object
                //Student student = dataSnapshot.child("Joey").getValue(Student.class);
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
                Log.d(TAG, "Student_ArrayList: " + Arrays.toString(students.toArray()));
                //System.out.println("Teacher_Array_List: " + Arrays.toString(teachers.toArray()));
                Log.d(TAG, "Teacher_Array_List: " + Arrays.toString(teachers.toArray()));

                Log.d(TAG, "Subject_Array_List: " + Arrays.toString(subjects.toArray()));
                Log.d(TAG, "Homework_Array_List: " + Arrays.toString(homeworks.toArray()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    } // basicReadWrite() End
}
