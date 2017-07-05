package com.formation.appli.bruxellesparcourbd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jeremyvdm on 02/07/2017.
 */

public class FresqueBD implements Parcelable {
    private int _fresqueId;
    private String titre;
    private String auteur;
    private int years;
    private Coordonees coordonees;
    private String ressourceImage;

    public FresqueBD(String titre, String auteur, int years, Coordonees coordonees, String ressourceImage) {
        this._fresqueId = -1;
        this.titre = titre;
        this.auteur = auteur;
        this.years = years;
        this.coordonees = coordonees;
        this.ressourceImage = ressourceImage;
    }

    public FresqueBD(int _fresqueId, String titre, String auteur, int years, Coordonees coordonees, String ressourceImage) {
        this._fresqueId = _fresqueId;
        this.titre = titre;
        this.auteur = auteur;
        this.years = years;
        this.coordonees = coordonees;
        this.ressourceImage = ressourceImage;
    }

    public void set_fresqueId(int _fresqueId){
        this._fresqueId = _fresqueId;
    }

    public int get_fresqueId(){
        return _fresqueId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public Coordonees getCoordonees() {
        return coordonees;
    }

    public void setCoordonees(Coordonees coordonees) {
        this.coordonees = coordonees;
    }

    public void setRessourceImage(String ressourceImage){
        this.ressourceImage = ressourceImage;
    }

    public String getRessourceImage(){
        return ressourceImage;
    }



    @Override
    public int describeContents() {
        return 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(_fresqueId);
        dest.writeString(titre);
        dest.writeString(auteur);
        dest.writeInt(years);
        dest.writeParcelable(coordonees,0);
        dest.writeString(ressourceImage);
    }

    protected FresqueBD(Parcel in) {
        _fresqueId = in.readInt();
        titre = in.readString();
        auteur = in.readString();
        years = in.readInt();
        coordonees = in.readParcelable(Coordonees.class.getClassLoader());
        ressourceImage = in.readString();
    }

    public static final Creator<FresqueBD> CREATOR = new Creator<FresqueBD>() {
        @Override
        public FresqueBD createFromParcel(Parcel in) {
            return new FresqueBD(in);
        }

        @Override
        public FresqueBD[] newArray(int size) {
            return new FresqueBD[size];
        }
    };

}
