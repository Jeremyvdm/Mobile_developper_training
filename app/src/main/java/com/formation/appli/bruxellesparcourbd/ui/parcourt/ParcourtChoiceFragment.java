package com.formation.appli.bruxellesparcourbd.ui.parcourt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.formation.appli.bruxellesparcourbd.R;


public class ParcourtChoiceFragment extends Fragment implements View.OnClickListener{

    private Button btn_parcourt_maps;
    private Button btn_parcourt_liste;
    private Button btn_parcourt_choix;

    public ParcourtChoiceFragment() {
        // Required empty public constructor
    }



    //region Singelton
    private static ParcourtChoiceFragment instance;

    public static ParcourtChoiceFragment getInstance(){
        if(instance == null){
            instance = new ParcourtChoiceFragment();
        }
        return instance;
    }


    //endregion

    //region Communication
    public interface ParcourtChoiceFragmentCallBack{
        void onClick(int id);
    }

    private ParcourtChoiceFragment.ParcourtChoiceFragmentCallBack callback;
    public void setcallback(ParcourtActivity callback){
        this.callback = (ParcourtChoiceFragmentCallBack) callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_parcourt_choice, container, false);
        initFragment(v);
        return v;
    }

    private void initFragment(View v){
        btn_parcourt_maps = (Button) v.findViewById(R.id.btn_parcourt_carte);
        btn_parcourt_liste = (Button) v.findViewById(R.id.btn_parcourt_play);
        btn_parcourt_choix = (Button) v.findViewById(R.id.btn_parcourt_play);

        btn_parcourt_maps.setOnClickListener(this);
        btn_parcourt_liste.setOnClickListener(this);
        btn_parcourt_choix.setOnClickListener(this);
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
