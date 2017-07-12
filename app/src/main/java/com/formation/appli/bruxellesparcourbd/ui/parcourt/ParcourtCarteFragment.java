package com.formation.appli.bruxellesparcourbd.ui.parcourt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.Coordonees;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.model.ParcourtBD;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class ParcourtCarteFragment extends Fragment{

    private Bundle extra;
    private ArrayList<FresqueBD> parcourFresqueBd;

    private GoogleMap googleMap;
    private MapView mapView;


    public ParcourtCarteFragment() {
        // Required empty public constructor
    }

    //region Singleton
    private static ParcourtCarteFragment instance;

    public static ParcourtCarteFragment getInstance() {
        if (instance == null) {
            instance = new ParcourtCarteFragment();
        }
        return instance;
    }


    //endregion

    //region Communication
    public interface ParcourtCarteFragmentFragmentCallback {
        void getDetailFresque(String titre);
    }

    private ParcourtCarteFragmentFragmentCallback callback;

    public void setCallback(ParcourtCarteFragmentFragmentCallback callback) {
        this.callback = callback;
    }
    //endregion
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        initvariable();
        initParcourt();
        View v = inflater.inflate(R.layout.fragment_parcourt_carte, container, false);
        v= initCarte(v, savedInstanceState);
        return v;
    }

    private void initvariable(){
        parcourFresqueBd = new ArrayList<>();
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

                ArrayList<MarkerOptions> parcourtMarkeur = addMarqueurPosition();

                LatLngBounds.Builder fresqueBdParcPosBuilder = new LatLngBounds.Builder();

                for (int i=0; i<parcourtMarkeur.size();i++){
                    final MarkerOptions markerFresque = parcourtMarkeur.get(i);
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
        for(int bd = 0; bd <parcourFresqueBd.size();bd++){
            FresqueBD fresqueBD = parcourFresqueBd.get(bd);
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

    private void initParcourt(){
        extra = this.getArguments();
        ParcourtBD parcourtBdObject = extra.getParcelable(ParcourtActivity.PARCOURT_BD_CHOISIS);
        parcourFresqueBd = parcourtBdObject.getParcourtFresqueBD();
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
