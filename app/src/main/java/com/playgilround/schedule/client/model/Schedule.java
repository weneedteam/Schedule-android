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

}
