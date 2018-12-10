package com.example.demo.model;

public class FaceEntryTime {
    private int id;
    private String face_id;
    private String entry_time;
    private int user_id;
    private int entry_hour;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int entry_minute;
    private int entry_second;

    public FaceEntryTime(int id,String face_id, String entry_time, int user_id, int entry_hour, int entry_minute, int entry_second) {
        this.face_id = face_id;
        this.entry_time = entry_time;
        this.user_id = user_id;
        this.entry_hour = entry_hour;
        this.entry_minute = entry_minute;
        this.entry_second = entry_second;
        this.id = id;
    }

    public FaceEntryTime(String face_id, String entry_time, int user_id, int entry_hour, int entry_minute, int entry_second) {
        this.face_id = face_id;
        this.entry_time = entry_time;
        this.user_id = user_id;
        this.entry_hour = entry_hour;
        this.entry_minute = entry_minute;
        this.entry_second = entry_second;
    }

    public String getFace_id() {
        return face_id;
    }

    public void setFace_id(String face_id) {
        this.face_id = face_id;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(String entry_time) {
        this.entry_time = entry_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEntry_hour() {
        return entry_hour;
    }

    public void setEntry_hour(int entry_hour) {
        this.entry_hour = entry_hour;
    }

    public int getEntry_minute() {
        return entry_minute;
    }

    public void setEntry_minute(int entry_minute) {
        this.entry_minute = entry_minute;
    }

    public int getEntry_second() {
        return entry_second;
    }

    public void setEntry_second(int entry_second) {
        this.entry_second = entry_second;
    }
}
