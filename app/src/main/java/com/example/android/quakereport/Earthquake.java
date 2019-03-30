package com.example.android.quakereport;

public class Earthquake {

    private String mLocation;
    private double mMagnitude;
    private long mTimeInMilliseconds;
    private String mWeb;

    public Earthquake (String location, double magnitude, long timeInMilliseconds, String web){
        mLocation = location;
        mMagnitude = magnitude;
        mTimeInMilliseconds = timeInMilliseconds;
        mWeb = web;
    }



    public String getmLocation() {
        return mLocation;
    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public long getmDate() {
        return mTimeInMilliseconds;
    }

    public String getmWeb(){return mWeb;}

}
