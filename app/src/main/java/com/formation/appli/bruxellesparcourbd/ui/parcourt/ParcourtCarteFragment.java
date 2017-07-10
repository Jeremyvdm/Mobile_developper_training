package com.formation.appli.bruxellesparcourbd.ui.parcourt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.formation.appli.bruxellesparcourbd.R;


public class ParcourtCarteFragment extends Fragment {

    private Bundle extra;
    private Button ButtonRetour;


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
}
