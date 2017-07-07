package com.formation.appli.bruxellesparcourbd.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.User;


public class FormulaireMainFragment extends Fragment{
    private EditText et_lastName;
    private EditText et_firstName;
    private EditText et_userName;
    private EditText et_email;
    private EditText et_password;

    private Button btn_confirm_info;

    private User newGreatUser;


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
        void onConfirm(User NewUser);
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

        btn_confirm_info = v.findViewById(R.id.btn_formulaire_confirm);
        btn_confirm_info.setEnabled(false);

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                btn_confirm_info.setEnabled(true);
            }
        });

        btn_confirm_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGreatUser  = newUser();
                sendCallBack(newGreatUser);
            }
        });



        return v;
    }

    private User newUser(){
        String lastName = et_lastName.getText().toString();
        String firstName = et_firstName.getText().toString();
        String userName = et_userName.getText().toString();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        User user = new User(lastName,firstName,userName,email,password);
        return user;
    }

    private void sendCallBack(User user) {
        callback.onConfirm(user);
    }

}
