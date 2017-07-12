package com.formation.appli.bruxellesparcourbd.ui.parcourt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.model.ParcourtBD;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import java.util.ArrayList;


public class ParcourtCarteFragment extends Fragment {

    private String titre;
    private Bundle extra;
    private ArrayList<FresqueBD> parcourFresqueBd;

    private GoogleMap googleMap;


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

        return v;
    }

    private void initvariable(){
        parcourFresqueBd = new ArrayList<>();
        extra = this.getArguments();
    }

    private void initCarte(){
        MapFragment fm = new MapFragment();
    }



    private void sendCallBack(String titre){
        if(callback !=null){
            callback.getDetailFresque(titre);
        }
    }

    private void initParcourt(){
        ParcourtBD parcourtBdObject = extra.getParcelable(ParcourtActivity.PARCOURT_BD_CHOISIS);
        parcourFresqueBd =parcourtBdObject.getParcourtFresqueBD();
        initCarte();
    }
}
