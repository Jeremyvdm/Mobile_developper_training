package com.formation.appli.bruxellesparcourbd.ui.main;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.formation.appli.bruxellesparcourbd.R;


public class AcceuilMainFragment extends Fragment implements View.OnClickListener{

    private EditText et_userName;
    private EditText et_password;

    private Button btn_connexion;
    private Button btn_new_user;


    public AcceuilMainFragment() {
        // Required empty public constructor
    }

    //region Singelton
    private static AcceuilMainFragment instance;

    public static AcceuilMainFragment getInstance(){
        if(instance == null){
            instance = new AcceuilMainFragment();
        }
        return instance;
    }
    //endregion

    //region Communication
    public interface AcceuilFragmentCallBack{
        void onLoging(String userName, String password);
    }

    private AcceuilFragmentCallBack callback;
    public void setcallback(MainActivity callback){
        this.callback = (AcceuilFragmentCallBack) callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_acceuil, container, false);

        v = initView(v);

        return v;
    }

    private View initView(View v) {
        et_userName =(EditText) v.findViewById(R.id.et_acceuil_UserName);
        et_password = (EditText) v.findViewById(R.id.et_acceuil_Password);

        btn_connexion = (Button) v.findViewById(R.id.btn_acceuil_connection);
        btn_new_user = (Button) v.findViewById(R.id.btn_acceuil_new_user);



        btn_new_user.setOnClickListener(this);
        btn_connexion.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_acceuil_connection:
                connection();
                break;
            case R.id.btn_acceuil_new_user:
                newUser();
                break;
        }
    }

    private void connection(){
        String userName = et_userName.getText().toString();
        String password = et_password.getText().toString();
        sendCallBack(userName,password);

    }

    private void sendCallBack(String userName, String password) {
        if (callback != null){
            callback.onLoging(userName,password);
        }
    }

    private void newUser(){
        FragmentManager fragman = getFragmentManager();
        FragmentTransaction fragTrans = fragman.beginTransaction();
        FormulaireMainFragment fromFrag = new FormulaireMainFragment();
        fragTrans.replace(R.id.fl_main_frame, fromFrag);
        fragTrans.commit();
    }

}
