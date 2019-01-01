package com.playgilround.schedule.client.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 18-12-31
 * Schedule RealmObject 생성
 */
public class Schedule extends RealmObject {

    @PrimaryKey
    private int id;
    private String title;
    private String location;
    private double latitude;
    private double longitude;
    private long time;
    private String desc;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
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

    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
