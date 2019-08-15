package com.playgilround.schedule.client.data;

public class ScheduleData {

    private String title;
    private String startDate;
    private String endDate;
    private String member;
    private String place;
    private String memo;
    private String map;
    private String result;

    public ScheduleData() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getMember() {
        return member;
    }

    public String getPlace() {
        return place;
    }

    public String getMemo() {
        return memo;
    }

    public String getMap() {
        return map;
    }

    public String getResult() {
        return result;
    }
}
