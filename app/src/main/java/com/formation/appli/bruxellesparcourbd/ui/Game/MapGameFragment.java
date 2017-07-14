package com.formation.appli.bruxellesparcourbd.ui.Game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.Coordonees;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.tools.LocalisationGPS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapGameFragment extends Fragment implements LocalisationGPS.LocalisationGPSCallBack{

    private Bundle extra;
    private FresqueBD fresqueBdPaly;

    private GoogleMap googleMap;
    private MapView mapView;

    private double maPositionLatitude;
    private double maPositionLongitude;

    private View v;
    private Bundle savedInstanceStateBundle;



    //region Singleton
    private static MapGameFragment instance;

    public static MapGameFragment getInstance() {
        if (instance == null) {
            instance = new MapGameFragment();
        }
        return instance;
    }




    //endregion


    public MapGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_map_game, container, false);
        initParcours();
        initCarte(v,savedInstanceState);
        loca();
        return v;
    }

    private void loca() {
        LocalisationGPS gps = new LocalisationGPS();
        gps.setCallback(this);
        gps.demande(getContext());
    }

    private void displayMarker(){
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMaps) {
                googleMap = mMaps;

                ArrayList<MarkerOptions> parcoursMarkeur = addMarqueurPosition();

                LatLngBounds.Builder fresqueBdParcPosBuilder = new LatLngBounds.Builder();

                for (int i=0; i<parcoursMarkeur.size();i++){
                    final MarkerOptions markerFresque = parcoursMarkeur.get(i);
                    fresqueBdParcPosBuilder.include(markerFresque.getPosition());
                    googleMap.addMarker(markerFresque);
                }

                // For zooming automatically to the location of the marker
                LatLngBounds fresqueBdParcPosBound = fresqueBdParcPosBuilder.build();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(fresqueBdParcPosBound,10));
                CameraUpdateFactory.zoomTo(15);
            }
        });
        mapView.onResume();

    }

    @Override
    public void localiser(double laptitude, double longitude) {
        Log.v("toto", "localisé !!!!");

        maPositionLatitude = laptitude;
        maPositionLongitude = longitude;
        displayMarker();
    }

    private View initCarte(View v, Bundle savedInstanceState){
        mapView = (MapView) v.findViewById(R.id.mV_game_frag_maps);
        mapView.onCreate(savedInstanceState);
        return v;
    }



    private ArrayList<MarkerOptions> addMarqueurPosition(){
        ArrayList<MarkerOptions> marqueurFresqueParcourtBd = new ArrayList<>();
        String titreFresque = fresqueBdPaly.getTitre();
        Coordonees coordoneesFresqueBdPaly = fresqueBdPaly.getCoordonees();
        LatLng positionFresque = new LatLng(coordoneesFresqueBdPaly.getLatitude(),coordoneesFresqueBdPaly.getLongitude());
        LatLng Maposition = new LatLng(maPositionLatitude,maPositionLongitude);
        marqueurFresqueParcourtBd.add(new MarkerOptions().position(positionFresque).title(titreFresque));
        marqueurFresqueParcourtBd.add(new MarkerOptions().position(Maposition).title("vous êtes ici"));


        return marqueurFresqueParcourtBd;
    }


    private void initParcours(){
        extra = this.getArguments();
        fresqueBdPaly = extra.getParcelable(GameActivity.FRESQUE_BD_PLAY);
    }



    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}


