package com.formation.appli.bruxellesparcourbd.ui.parcourt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.formation.appli.bruxellesparcourbd.Asynch.JsonParcourtBD;
import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.ui.User.UserActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import java.util.ArrayList;


public class ParcourtCarteFragment extends Fragment implements JsonParcourtBD.JsonParcourtBDCallBack{

    private Bundle extra;
    private Button ButtonRetour;
    private ArrayList<FresqueBD> parcourFresqueBd;
    private int numeroParcourChoisi;

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
        void retourMenu();
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
        v = initFragment(v);

        return v;
    }

    private void initvariable(){
        parcourFresqueBd = new ArrayList<>();
        extra = this.getArguments();
    }

    private void initParcourt(){
        numeroParcourChoisi = extra.getInt(UserActivity.NUMERODEPARCOURT);
        JsonParcourtBD parcourJson= new JsonParcourtBD();
        parcourJson.setCallback(this);
    }

    private void initCarte(){
        MapFragment fm = new MapFragment();
    }

    private View initFragment(View v){
        ButtonRetour = (Button) v.findViewById(R.id.btn_parcourt_maps_retour);




        ButtonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCallBack();
            }
        });
        return v;
    }

    private void sendCallBack(){
        if(callback !=null){
            callback.retourMenu();
        }
    }

    @Override
    public void parcourt(ArrayList<FresqueBD> parcourBDJson) {
        Toast.makeText(this.getActivity(),"le parcourt a été correctemenet chargé", Toast.LENGTH_SHORT).show();
        parcourFresqueBd = parcourBDJson;
        initCarte();
    }
}
