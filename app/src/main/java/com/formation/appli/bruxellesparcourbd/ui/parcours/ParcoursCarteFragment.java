package com.formation.appli.bruxellesparcourbd.ui.parcours;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.Coordonees;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.model.ParcoursBD;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class ParcoursCarteFragment extends Fragment{

    private Bundle extra;
    private ArrayList<FresqueBD> parcoursFresqueBd;

    private GoogleMap googleMap;
    private MapView mapView;


    public ParcoursCarteFragment() {
        // Required empty public constructor
    }

    //region Singleton
    private static ParcoursCarteFragment instance;

    public static ParcoursCarteFragment getInstance() {
        if (instance == null) {
            instance = new ParcoursCarteFragment();
        }
        return instance;
    }


    //endregion

    //region Communication
    public interface ParcoursCarteFragmentFragmentCallback {
        void getDetailFresque(String titre);
    }

    private ParcoursCarteFragmentFragmentCallback callback;

    public void setCallback(ParcoursCarteFragmentFragmentCallback callback) {
        this.callback = callback;
    }
    //endregion
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        initvariable();
        initParcours();
        View v = inflater.inflate(R.layout.fragment_parcours_carte, container, false);
        v= initCarte(v, savedInstanceState);
        return v;
    }

    private void initvariable(){
        parcoursFresqueBd = new ArrayList<>();
        extra = this.getArguments();
    }

    private View initCarte(View v, Bundle savedInstanceState){
        mapView = (MapView) v.findViewById(R.id.mV_parc_frag_maps);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();


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

                    googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener(){
                        @Override
                        public void onInfoWindowClick(Marker marker){
                            String titre = marker.getTitle();
                            sendCallBack(titre);
                        }
                    });
                }

                // For zooming automatically to the location of the marker
                LatLngBounds fresqueBdParcPosBound = fresqueBdParcPosBuilder.build();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(fresqueBdParcPosBound,10));
                CameraUpdateFactory.zoomTo(15);
            }
        });
        return v;
    }


    private ArrayList<MarkerOptions> addMarqueurPosition(){
        ArrayList<MarkerOptions> marqueurFresqueParcourtBd = new ArrayList<>();
        for(int bd = 0; bd < parcoursFresqueBd.size(); bd++){
            FresqueBD fresqueBD = parcoursFresqueBd.get(bd);
            String titreFresque = fresqueBD.getTitre();
            Coordonees coordoneesFresque = fresqueBD.getCoordonees();
            LatLng positionFresque = new LatLng(coordoneesFresque.getLatitude(),coordoneesFresque.getLongitude());
            marqueurFresqueParcourtBd.add(new MarkerOptions().position(positionFresque).title(titreFresque));

        }

        return marqueurFresqueParcourtBd;
    }

    private void sendCallBack(String titre){
        if(callback !=null){
            callback.getDetailFresque(titre);
        }
    }

    private void initParcours(){
        extra = this.getArguments();
        ParcoursBD parcoursBdObject = extra.getParcelable(ParcoursActivity.PARCOURS_BD_CHOISIS);
        parcoursFresqueBd = parcoursBdObject.getParcoursFresqueBD();
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
