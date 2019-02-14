package com.playgilround.schedule.client.model;

/**
 * 19-01-13
 */
public class ScheduleInfo {
    //    public Bitmap image;
    public int id;
    public long time;
    public String title;
    public String desc;

    public ScheduleInfo(int id, long time, String title, String desc) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.desc = desc;
    }
}

