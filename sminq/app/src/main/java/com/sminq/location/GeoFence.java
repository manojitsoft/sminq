package com.sminq.location;

/**
 * Created by Manojkumar on 7/31/2017.
 */

public class GeoFence {

    private double latitude;

    private double longitude;

    public GeoFence(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
