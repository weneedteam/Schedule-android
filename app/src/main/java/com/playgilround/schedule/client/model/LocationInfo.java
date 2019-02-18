package com.playgilround.schedule.client.model;

/**
 * 19-02-18
 * LocationInfo 관련
 */
public class LocationInfo {
    public String location;
    public double latitude;
    public double longitude;

    public LocationInfo(String location, double latitude, double longitude) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
