package com.formation.appli.bruxellesparcourbd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jeremyvdm on 03/07/2017.
 */
// Pour récurer la postion latitude et longitude
public class Coordonees implements Parcelable{
    private double longitude;

    private double latitude;

    public Coordonees(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
    }

    protected Coordonees(Parcel in) {
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    public static final Creator<Coordonees> CREATOR = new Creator<Coordonees>() {
        @Override
        public Coordonees createFromParcel(Parcel in) {
            return new Coordonees(in);
        }

        @Override
        public Coordonees[] newArray(int size) {
            return new Coordonees[size];
        }
    };

}
