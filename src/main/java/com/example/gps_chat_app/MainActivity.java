package com.example.gps_chat_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    Button messageButton;
    Button mapButton;
    Button textButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageButton = (Button) findViewById(R.id.sendMessageButton);

        // Set functionality of the menu buttons
        messageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,
                        MessageActivity.class);
                startActivity(myIntent);
            }
        });

        mapButton = (Button) findViewById(R.id.mapViewButton);

        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,
                        MapsActivity.class);
                startActivity(myIntent);
            }
        });

        textButton = (Button) findViewById(R.id.textViewButton);

        textButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,
                        TextActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
