package com.playgilround.schedule.client.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Event extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private int color;
    private int icon;
    private boolean isChecked;
}
