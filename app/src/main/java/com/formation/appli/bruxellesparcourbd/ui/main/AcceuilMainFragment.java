package com.formation.appli.bruxellesparcourbd.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.formation.appli.bruxellesparcourbd.R;


public class AcceuilMainFragment extends Fragment implements View.OnClickListener{

    private EditText et_email;
    private EditText et_password;

    private Button btn_connexion;
    private Button btn_new_user;
    private Button btn_continue;


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
        void onCLickChoice(int id, String email,String password);
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

        v = initFragment(v);

        return v;
    }

    private View initFragment(View v) {
        et_email =(EditText) v.findViewById(R.id.et_acceuil_email);
        et_password = (EditText) v.findViewById(R.id.et_acceuil_Password);

        btn_connexion = (Button) v.findViewById(R.id.btn_acceuil_connection);
        btn_new_user = (Button) v.findViewById(R.id.btn_acceuil_new_user);
        btn_continue = (Button) v.findViewById(R.id.btn_acceuil_continue);

        btn_continue.setEnabled(false);

        btn_new_user.setOnClickListener(this);
        btn_connexion.setOnClickListener(this);
        btn_continue.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        sendCallBack(view.getId());
    }


    private void sendCallBack(int id) {
        if (callback != null){
            String email = et_email.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            callback.onCLickChoice(id,email,password);
        }
    }



}
