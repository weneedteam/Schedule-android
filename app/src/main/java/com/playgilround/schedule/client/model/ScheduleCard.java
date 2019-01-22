package com.playgilround.schedule.client.model;

import android.graphics.Bitmap;

/**
 * 19-01-13
 */
public class ScheduleCard {
//    public Bitmap image;
    public int id;
    public long time;
    public String title;
    public String desc;

    public ScheduleCard(int id, long time, String title, String desc) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.desc = desc;
    }

    /*public Bitmap getImage() {
=======
    public Bitmap getImage() {
>>>>>>> 6669995f2d98edb835f4a2f9d0c45e36b0f8b428
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
<<<<<<< HEAD
    }*/
    }

