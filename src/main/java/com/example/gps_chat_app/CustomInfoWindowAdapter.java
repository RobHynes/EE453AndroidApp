package com.example.gps_chat_app;

import com.google.android.gms.maps.model.Marker;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;

import android.view.LayoutInflater;

class Custominfowindowadapter implements InfoWindowAdapter {
    private View mymarkerview;
    public Context context;

    // Custom info window for displaying messages on the map markers
    Custominfowindowadapter(Context context) {
        this.context = context;
        mymarkerview = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custominfowindow, null);
    }

    // Use default white window
    public View getInfoWindow(Marker marker) {
        return null;
    }

    public View getInfoContents(Marker marker) {
        render(marker, mymarkerview);
        return mymarkerview;
    }

    // Get custom info contents, use custom layout with text view for original message and text view for appended messages
    private View render(Marker marker, View view) {
        TextView originalMessage = view.findViewById(R.id.originalMessage);
        TextView appendedMessage = view.findViewById(R.id.appendedMessage);

        originalMessage.setText(marker.getTitle());
        appendedMessage.setText(marker.getSnippet());
        return view;
    }
}
