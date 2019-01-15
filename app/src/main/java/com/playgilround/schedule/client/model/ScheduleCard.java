package com.playgilround.schedule.client.model;

import android.graphics.Bitmap;

/**
 * 19-01-13
 */
public class ScheduleCard {
//    public Bitmap image;
    public String time;
    public String title;
    public String desc;

    public ScheduleCard(String time, String title, String desc) {
        this.time = time;
        this.title = title;
        this.desc = desc;
    }

    /*public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }*/
}
