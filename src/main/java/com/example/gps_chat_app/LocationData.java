package com.example.gps_chat_app;

public class LocationData {

    public double latitude;
    public double longitude;
    public String message;

    public LocationData() {}

    // LocationData object containing latitude and longitude data and message string
    public LocationData(double lat, double lon, String msg)
    {
        latitude = lat;
        longitude = lon;
        message = msg;
    }
}
