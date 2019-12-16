package com.example.calendarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.ArrayList;
import java.util.HashMap;

public class SubscribeActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference CalendarDatabase = database.getReference();

    private DatabaseReference mDatabase;
    private ListView schedule;
    private CalendarView oneCalendar;
    private ArrayList<String> mDetails = new ArrayList<>(); // Details of timeslot
    private ArrayList<String> mKeys = new ArrayList<>(); // Timeslot or Task

    private static final String TAG = "SubscribeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        Intent newIntent = getIntent();
        final String current_name = newIntent.getStringExtra("name");

        schedule = (ListView) findViewById(R.id.schedule);
        oneCalendar = (CalendarView) findViewById(R.id.oneCalendar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]

        Button subscribe_50_001 = findViewById(R.id.subscribe_50_001);
        subscribe_50_001.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Subscribing to 50.001");
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("50.001")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg = getString(R.string.msg_subscribed);
                                if (!task.isSuccessful()) {
                                    msg = getString(R.string.msg_subscribe_failed);
                                }
                                Log.d(TAG, msg);
                                Toast.makeText(SubscribeActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                // [END subscribe_topics]

                DatabaseReference fromPath = CalendarDatabase.child("Subject")
                        .child("Introduction to Information Systems and Programming").child("Schedule");
                final DatabaseReference toPath = CalendarDatabase.child("Student").child(current_name).child("Schedule");
                fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot copy : dataSnapshot.getChildren()) {
                            String Key = copy.getKey();
                            //String Value = copy.getValue(String.class);
                            //toPath.child(Key).setValue(Value);
                            for (DataSnapshot copycopy : copy.getChildren()){
                                String KeyKey = copycopy.getKey();
                                String ValueValue = copycopy.getValue(String.class);
                                toPath.child(Key).child(KeyKey).setValue(ValueValue);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        Button subscribe_50_002 = findViewById(R.id.subscribe_50_002);
        subscribe_50_002.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Subscribing to 50.002");
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("50.002")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg = getString(R.string.msg_subscribed);
                                if (!task.isSuccessful()) {
                                    msg = getString(R.string.msg_subscribe_failed);
                                }
                                Log.d(TAG, msg);
                                Toast.makeText(SubscribeActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                // [END subscribe_topics]
                DatabaseReference fromPath = CalendarDatabase.child("Subject")
                        .child("Computer Structures").child("Schedule");
                final DatabaseReference toPath = CalendarDatabase.child("Student").child(current_name).child("Schedule");
                fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot copy : dataSnapshot.getChildren()) {
                            String Key = copy.getKey();
                            //String Value = copy.getValue(String.class);
                            //toPath.child(Key).setValue(Value);
                            for (DataSnapshot copycopy : copy.getChildren()){
                                String KeyKey = copycopy.getKey();
                                String ValueValue = copycopy.getValue(String.class);
                                toPath.child(Key).child(KeyKey).setValue(ValueValue);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        Button subscribe_50_004 = findViewById(R.id.subscribe_50_004);
        subscribe_50_004.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Subscribing to 50004");
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("50.004")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg = getString(R.string.msg_subscribed);
                                if (!task.isSuccessful()) {
                                    msg = getString(R.string.msg_subscribe_failed);
                                }
                                Log.d(TAG, msg);
                                Toast.makeText(SubscribeActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                // [END subscribe_topics]

                DatabaseReference fromPath = CalendarDatabase.child("Subject")
                        .child("Introduction To Algorithms").child("Schedule");
                final DatabaseReference toPath = CalendarDatabase.child("Student").child(current_name).child("Schedule");
                fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot copy : dataSnapshot.getChildren()) {
                            String Key = copy.getKey();
                            //String Value = copy.getValue(String.class);
                            //toPath.child(Key).setValue(Value);
                            for (DataSnapshot copycopy : copy.getChildren()){
                                String KeyKey = copycopy.getKey();
                                String ValueValue = copycopy.getValue(String.class);
                                toPath.child(Key).child(KeyKey).setValue(ValueValue);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    public void moveFirebaseRecord(DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(getApplicationContext(), "COPY FAILED", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "COPY SUCCESS", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "onCancelled- copy fail", Toast.LENGTH_LONG).show();

            }
        });
    }

}
//    private void copyRecord(DatabaseReference fromPath, final DatabaseReference toPath) {
//        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                toPath.setValue(dataSnapshot.getValue().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Log.d(TAG, "Success!");
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }

//        Button logTokenButton = findViewById(R.id.logTokenButton);
//        logTokenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get token
//                // [START retrieve_current_token]
//                FirebaseInstanceId.getInstance().getInstanceId();
//                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                                if (!task.isSuccessful()) {
//                                    Log.w(TAG, "getInstanceId failed", task.getException());
//                                    return;
//                                }
//
//                                // Get new Instance ID token
//                                String token = task.getResult().getToken();
//
//                                // Log and toast
//                                String msg = getString(R.string.msg_token_fmt, token);
//                                Log.d(TAG, msg);
//                                Toast.makeText(SubscribeActivity.this, msg, Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                // [END retrieve_current_token]
//            }
//        });


