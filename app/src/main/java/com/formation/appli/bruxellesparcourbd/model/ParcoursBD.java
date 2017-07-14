package com.formation.appli.bruxellesparcourbd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Jeremyvdm on 02/07/2017.
 */
// Initialisation des parcourds bd
public class ParcoursBD implements Parcelable {

    private int parcoursBdID;
    private ArrayList<FresqueBD> parcoursFresqueBD;

    public ParcoursBD(ArrayList<FresqueBD> parcoursFresqueBD){
        parcoursBdID = -1;
        this.parcoursFresqueBD = parcoursFresqueBD;
    }

    public ParcoursBD(int parcoursBdID, ArrayList<FresqueBD> parcoursFresqueBD){
        this.parcoursBdID = parcoursBdID;
        this.parcoursFresqueBD = parcoursFresqueBD;
    }

    public void addFrequeBd(FresqueBD fbd){
        parcoursFresqueBD.add(fbd);
    }

    public void removeFresqueBd(FresqueBD fbd){
        for(int i = 0; i< parcoursFresqueBD.size(); i++){
            FresqueBD fbd2 = parcoursFresqueBD.get(i);
            if(fbd2.get_fresqueId() == fbd.get_fresqueId()){
                parcoursFresqueBD.remove(fbd.get_fresqueId());
            }
        }
    }

    public ArrayList<FresqueBD> getParcoursFresqueBD() {
        return parcoursFresqueBD;
    }

    public void setParcoursFresqueBD(ArrayList<FresqueBD> parcoursFresqueBD) {
        this.parcoursFresqueBD = parcoursFresqueBD;
    }


    @Override
    public int describeContents() {
        int size = parcoursFresqueBD.size();
        return size;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(parcoursBdID);
        parcel.writeTypedList(parcoursFresqueBD);
    }

    protected ParcoursBD(Parcel in) {
        parcoursBdID = in.readInt();
        parcoursFresqueBD = in.createTypedArrayList(FresqueBD.CREATOR);
    }

    public static final Creator<ParcoursBD> CREATOR = new Creator<ParcoursBD>() {
        @Override
        public ParcoursBD createFromParcel(Parcel in) {
            return new ParcoursBD(in);
        }

        @Override
        public ParcoursBD[] newArray(int size) {
            return new ParcoursBD[size];
        }
    };
}
