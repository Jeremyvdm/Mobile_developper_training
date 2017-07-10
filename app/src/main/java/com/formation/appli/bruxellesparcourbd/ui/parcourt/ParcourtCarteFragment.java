package com.formation.appli.bruxellesparcourbd.ui.parcourt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.formation.appli.bruxellesparcourbd.Asynch.JsonParcourtBD;
import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.ui.User.UserActivity;

import java.util.ArrayList;


public class ParcourtCarteFragment extends Fragment implements JsonParcourtBD.JsonParcourtBDCallBack{

    private Bundle extra;
    private Button ButtonRetour;
    private FrameLayout frL_maps;
    private ArrayList<FresqueBD> parcourFresqueBd;
    private int numéroParcourChoisi;


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
        initParcourt();
        View v = inflater.inflate(R.layout.fragment_parcourt_carte, container, false);
        v = initFragment(v);

        return v;
    }

    private void initParcourt(){
        parcourFresqueBd = new ArrayList<>();
        extra = this.getArguments();
        numéroParcourChoisi = extra.getInt(UserActivity.NUMERODEPARCOURT);
        JsonParcourtBD parcourJson= new JsonParcourtBD();
        parcourJson.setCallback(this);
        parcourJson.execute(numéroParcourChoisi);
        parcourFresqueBd = parcourJson.getArrayFresque();
    }

    private View initFragment(View v){
        ButtonRetour = (Button) v.findViewById(R.id.btn_parcourt_maps_retour);
        frL_maps = (FrameLayout) v.findViewById(R.id.fl_parcourt_frame_maps);
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
    public void parcourt() {
        Toast.makeText(this.getActivity(),"le parcourt a été correctemenet chargé", Toast.LENGTH_SHORT).show();
    }
}
