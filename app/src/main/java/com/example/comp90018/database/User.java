package com.example.comp90018.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Entity class of user keeps the object of user's emotion, activity, location and time
 */
@Entity(tableName = "user")
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "emotion")
    private String emotion;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "year")
    private String year;

    @ColumnInfo(name = "month")
    private String month;

    @ColumnInfo(name = "day")
    private String day;

    @ColumnInfo(name = "activity")
    private String activity;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmotion() {
        if (emotion.equals("NOT HUMAN") || emotion.equals("More than one face detected")){
            return "UNKNOWN";
        }
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
