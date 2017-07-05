package com.formation.appli.bruxellesparcourbd.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.User;


public class FormulaireMainFragment extends Fragment{
    private EditText et_lastName;
    private EditText et_firstName;
    private EditText et_userName;
    private EditText et_email;
    private EditText et_password;
    private EditText et_confirmPassword;

    private Button btn_confirm_info;


    public FormulaireMainFragment() {
        // Required empty public constructor
    }

    //region Singelton
    private static FormulaireMainFragment instance;

    public static FormulaireMainFragment getInstance(){
        if(instance == null){
            instance = new FormulaireMainFragment();
        }
        return instance;
    }
    //endregion

    //region Communication
    public interface FormulaireFragmentCallBack{
        void onConfirm(String userName);
    }

    private FormulaireMainFragment.FormulaireFragmentCallBack callback;
    public void setcallback(MainActivity callback){
        this.callback = (FormulaireFragmentCallBack) callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_formulaire, container, false);

        v = initView(v);

        return v;
    }

    private View initView(View v){
        et_lastName = v.findViewById(R.id.et_formulaire_Last_name);
        et_firstName = v.findViewById(R.id.et_formulaire_first_name);
        et_userName = v.findViewById(R.id.et_formulaire_user_name);
        et_email = v.findViewById(R.id.et_formulaire_email);
        et_password = v.findViewById(R.id.et_formulaire_password);
        et_confirmPassword = v.findViewById(R.id.et_formulaire_confirm_password);

        btn_confirm_info = v.findViewById(R.id.btn_formulaire_confirm);

        btn_confirm_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUser();
            }
        });

        return v;
    }

    private void newUser(){
        String lastName = et_lastName.getText().toString();
        String firstName = et_firstName.getText().toString();
        String userName = et_userName.getText().toString();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String confirmPassword = et_confirmPassword.getText().toString();
        if(password == confirmPassword){
            User newUser = new User(lastName,firstName,userName,email,password,confirmPassword);
            sendCallBack(userName);
        }
        else{
            Toast.makeText(this.getContext(),getString(R.string.toast_not_same_password),Toast.LENGTH_LONG).show();
        }

    }

    private void sendCallBack(String userName) {
        callback.onConfirm(userName);
    }

}
