package com.example.gps_chat_app;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageActivity extends Activity implements LocationListener {

    Button sendButton;
    Button backButton;
    EditText message;
    String msg;

    // Reference to Firebase database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("messages");

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.activity_message);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        sendButton = (Button) findViewById(R.id.sendButton);
        backButton = (Button) findViewById(R.id.backButton);
        message = (EditText) findViewById(R.id.enterText);

        // Capture button clicks
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                sendMessage();
            }
        });
    }

    // Send message to db
    private void sendMessage() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            onLocationChanged(location);
                        }
                    }
                });
    }

    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Get message text typed in EditText element
        msg = message.getText().toString();
        // Clear EditText element
        message.getText().clear();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = sdf.format(new Date());

        // Create LocationData object to send to db
        LocationData locationData = new LocationData(latLng.latitude, latLng.longitude, msg);

        // Create db entry containing LocationData and current date and time as the key
        myRef.child(currentDate).setValue(locationData);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
