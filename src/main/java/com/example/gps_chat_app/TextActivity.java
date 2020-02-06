package com.example.gps_chat_app;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TextActivity extends Activity {

    Button backButton;

    // ArrayLists to track textViews and locations for appending messages
    ArrayList<TextView> tvArray = new ArrayList<>();
    ArrayList<Location> locations = new ArrayList<>();
    LinearLayout layout;
    boolean appended = false;

    // Reference to Firebase database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("messages");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from text_activity.xml
        setContentView(R.layout.activity_text);

        backButton = (Button) findViewById(R.id.backButton);

        // Capture button clicks
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                finish();
            }
        });

//        layout = (LinearLayout) findViewById(R.id.layout);

        final Context context = this;
        final LinearLayout linLayout = (LinearLayout) this.findViewById(R.id.linLayout);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                // Get location data from database entry
                LocationData location = dataSnapshot.getValue(LocationData.class);
                // Get date from database entry
                String date = dataSnapshot.getKey();
                // Create new Location object for distance check
                Location e = new Location("");
                e.setLatitude(location.latitude);
                e.setLongitude(location.longitude);
                appended = false;

                // Iterate through all message locations and check distance to the new message
                for(int i = 0; i < locations.size(); i++)
                {
                    // If distance between current location and a previous location is 10 meters or less, append the message to the existing textView
                    if(e.distanceTo(locations.get(i)) <= 10)
                    {
                        appended = true;
                        String oldM = (String)tvArray.get(i).getText();
                        tvArray.get(i).setText(oldM + "\n\t\t\t" + date + ": " + location.message);
                        break;
                    }
                }

                // If location is in unique location, create a new textView with the message as the first entry
                if(appended == false)
                {
                    TextView textView = new TextView(context);
                    textView.setText(date + ": " + location.message);
                    tvArray.add(textView);
                    locations.add(e);
                    linLayout.addView(textView);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
