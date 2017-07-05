package com.formation.appli.bruxellesparcourbd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Jeremyvdm on 02/07/2017.
 */

public class ParcourtBD implements Parcelable {

    private int parcourtBdID;
    private ArrayList<FresqueBD> parcourtFresqueBD;

    public ParcourtBD(ArrayList<FresqueBD> parcourtFresqueBD){
        parcourtBdID = -1;
        this.parcourtFresqueBD = parcourtFresqueBD;
    }

    public ParcourtBD(int parcourtBdID, ArrayList<FresqueBD> parcourtFresqueBD){
        this.parcourtBdID = parcourtBdID;
        this.parcourtFresqueBD = parcourtFresqueBD;
    }

    public void addFrequeBd(FresqueBD fbd){
        parcourtFresqueBD.add(fbd);
    }

    public void removeFresqueBd(FresqueBD fbd){
        for(int i=0; i<parcourtFresqueBD.size(); i++){
            FresqueBD fbd2 = parcourtFresqueBD.get(i);
            if(fbd2.get_fresqueId() == fbd.get_fresqueId()){
                parcourtFresqueBD.remove(fbd.get_fresqueId());
            }
        }
    }

    public ArrayList<FresqueBD> getParcourtFresqueBD() {
        return parcourtFresqueBD;
    }

    public void setParcourtFresqueBD(ArrayList<FresqueBD> parcourtFresqueBD) {
        this.parcourtFresqueBD = parcourtFresqueBD;
    }


    @Override
    public int describeContents() {
        int size = parcourtFresqueBD.size();
        return size;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(parcourtBdID);
        parcel.writeTypedList(parcourtFresqueBD);
    }

    protected ParcourtBD(Parcel in) {
        parcourtBdID = in.readInt();
        parcourtFresqueBD = in.createTypedArrayList(FresqueBD.CREATOR);
    }

    public static final Creator<ParcourtBD> CREATOR = new Creator<ParcourtBD>() {
        @Override
        public ParcourtBD createFromParcel(Parcel in) {
            return new ParcourtBD(in);
        }

        @Override
        public ParcourtBD[] newArray(int size) {
            return new ParcourtBD[size];
        }
    };
}
