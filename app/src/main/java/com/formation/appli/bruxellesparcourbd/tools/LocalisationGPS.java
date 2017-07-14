package com.formation.appli.bruxellesparcourbd.tools;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Jeremyvdm on 12/07/2017.
 */
// classe qui va permettre une Localisation GPS
public class LocalisationGPS  implements
        LocationListener  {

    private  LocationManager locationManager;

    //region Callback
    public interface LocalisationGPSCallBack {
        void localiser(double laptitude, double longitude);
    }

    private LocalisationGPSCallBack callback;

    public void setCallback(LocalisationGPSCallBack callback) {
        this.callback = callback;
    }
    //endregion

    public void demande(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            if(callback != null) {
                callback.localiser(50,4);
            }
            return;
        }

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10,0,this);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitudeMaPosition = location.getLatitude();
        double longitudeMaPosition = location.getLongitude();
        if(callback != null) {
            callback.localiser(latitudeMaPosition, longitudeMaPosition);
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        String newStatus = "";
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                newStatus = "OUT_OF_SERVICE";
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                newStatus = "TEMPORARILY_UNAVAILABLE";
                break;
            case LocationProvider.AVAILABLE:
                newStatus = "AVAILABLE";
                break;
        }
        String msg = "gps is disable";
    }

}