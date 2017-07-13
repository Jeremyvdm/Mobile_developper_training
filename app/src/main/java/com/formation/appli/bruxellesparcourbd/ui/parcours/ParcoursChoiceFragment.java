package com.formation.appli.bruxellesparcourbd.ui.parcours;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.formation.appli.bruxellesparcourbd.R;


public class ParcoursChoiceFragment extends Fragment implements View.OnClickListener{

    private Button btn_parcours_maps;
    private Button btn_parcours_liste;
    private Button btn_parcours_choix;

    public ParcoursChoiceFragment() {
        // Required empty public constructor
    }



    //region Singelton
    private static ParcoursChoiceFragment instance;

    public static ParcoursChoiceFragment getInstance(){
        if(instance == null){
            instance = new ParcoursChoiceFragment();
        }
        return instance;
    }


    //endregion

    //region Communication
    public interface ParcoursChoiceFragmentCallBack {
        void onClick(int id);
    }

    private ParcoursChoiceFragmentCallBack callback;
    public void setcallback(ParcoursActivity callback){
        this.callback = (ParcoursChoiceFragmentCallBack) callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_parcours_choice, container, false);
        v= initFragment(v);
        return v;
    }

    private View initFragment(View v){
        btn_parcours_maps = (Button) v.findViewById(R.id.btn_parcours_carte);
        btn_parcours_liste = (Button) v.findViewById(R.id.btn_parcours_liste_fresque);
        btn_parcours_choix = (Button) v.findViewById(R.id.btn_parcours_play);

        btn_parcours_maps.setOnClickListener(this);
        btn_parcours_liste.setOnClickListener(this);
        btn_parcours_choix.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        sendCallBack(view.getId());
    }


    private void sendCallBack(int id){
        if (callback != null){
            callback.onClick(id);
        }
    }
}
