package com.formation.appli.bruxellesparcourbd.ui.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.formation.appli.bruxellesparcourbd.R;


public class ParcourtUserFragment extends Fragment implements View.OnClickListener{

    private Button btn_parcourt_1;
    private Button btn_parcourt_2;
    private Button btn_parcourt_3;
    private Button btn_parcourt_4;
    private Button btn_parcourt_5;
    private int parcourtNumber;

    public ParcourtUserFragment() {
        // Required empty public constructor
    }

    //region Singelton
    private static ParcourtUserFragment instance;

    public static ParcourtUserFragment getInstance(){
        if(instance == null){
            instance = new ParcourtUserFragment();
        }
        return instance;
    }

    //endregion

    //region Communication
    public interface ParcourtUserFragmentCallBack{
       void parcourtActivity(int parcourtNumber);
    }

    private ParcourtUserFragmentCallBack callback;
    public void setcallback(UserActivity callback){
        this.callback = (ParcourtUserFragmentCallBack) callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_parcourt, container, false);
        initFragment(v);

        return v;
    }

    private void initFragment(View v){
        btn_parcourt_1 = (Button) v.findViewById(R.id.btn_user_parcourt_1);
        btn_parcourt_2 = (Button) v.findViewById(R.id.btn_user_parcourt_2);
        btn_parcourt_3 = (Button) v.findViewById(R.id.btn_user_parcourt_3);
        btn_parcourt_4 = (Button) v.findViewById(R.id.btn_user_parcourt_4);
        btn_parcourt_5 = (Button) v.findViewById(R.id.btn_user_parcourt_5);

        btn_parcourt_1.setOnClickListener(this);
        btn_parcourt_2.setOnClickListener(this);
        btn_parcourt_3.setOnClickListener(this);
        btn_parcourt_4.setOnClickListener(this);
        btn_parcourt_5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_user_parcourt_1:
                parcourtNumber = 1;
                break;
            case R.id.btn_user_parcourt_2:
                parcourtNumber = 2;
                break;
            case R.id.btn_user_parcourt_3:
                parcourtNumber = 3;
                break;
            case R.id.btn_user_parcourt_4:
                parcourtNumber = 4;
                break;
            case R.id.btn_user_parcourt_5:
                parcourtNumber =5;
                break;
        }
        sendCallBack(parcourtNumber);
    }

    private void sendCallBack(int parcourtNumber){
        if(callback != null){
            callback.parcourtActivity(parcourtNumber);
        }
    }


}
