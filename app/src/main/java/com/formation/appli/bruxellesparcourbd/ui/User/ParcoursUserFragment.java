package com.formation.appli.bruxellesparcourbd.ui.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.formation.appli.bruxellesparcourbd.R;


public class ParcoursUserFragment extends Fragment implements View.OnClickListener{

    private Button btn_parcours_1;
    private Button btn_parcours_2;
    private Button btn_parcours_3;
    private Button btn_parcours_4;
    private Button btn_parcours_5;
    private int parcoursNumber;

    public ParcoursUserFragment() {
        // Required empty public constructor
    }

    //region Singelton
    private static ParcoursUserFragment instance;

    public static ParcoursUserFragment getInstance(){
        if(instance == null){
            instance = new ParcoursUserFragment();
        }
        return instance;
    }

    //endregion

    //region Communication
    public interface ParcoursUserFragmentCallBack {
       void parcourtActivity(int parcourtNumber);
    }

    private ParcoursUserFragmentCallBack callback;
    public void setcallback(UserActivity callback){
        this.callback = (ParcoursUserFragmentCallBack) callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_parcours, container, false);
        initFragment(v);

        return v;
    }

    private void initFragment(View v){
        btn_parcours_1 = (Button) v.findViewById(R.id.btn_user_parcours_1);
        btn_parcours_2 = (Button) v.findViewById(R.id.btn_user_parcours_2);
        btn_parcours_3 = (Button) v.findViewById(R.id.btn_user_parcours_3);
        btn_parcours_4 = (Button) v.findViewById(R.id.btn_user_parcours_4);
        btn_parcours_5 = (Button) v.findViewById(R.id.btn_user_parcours_5);

        btn_parcours_1.setOnClickListener(this);
        btn_parcours_2.setOnClickListener(this);
        btn_parcours_3.setOnClickListener(this);
        btn_parcours_4.setOnClickListener(this);
        btn_parcours_5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_user_parcours_1:
                parcoursNumber = 1;
                break;
            case R.id.btn_user_parcours_2:
                parcoursNumber = 2;
                break;
            case R.id.btn_user_parcours_3:
                parcoursNumber = 3;
                break;
            case R.id.btn_user_parcours_4:
                parcoursNumber = 4;
                break;
            case R.id.btn_user_parcours_5:
                parcoursNumber =5;
                break;
        }
        sendCallBack(parcoursNumber);
    }

    private void sendCallBack(int parcoursNumber){
        if(callback != null){
            callback.parcourtActivity(parcoursNumber);
        }
    }


}
