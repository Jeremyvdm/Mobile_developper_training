package com.formation.appli.bruxellesparcourbd.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
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


public class FormulaireMainFragment extends Fragment implements View.OnClickListener{
    public static View vFormulaire;
    private EditText et_lastName;
    private EditText et_firstName;
    private EditText et_userName;
    private EditText et_email;
    private EditText et_password;

    private Button btn_confirm_info;
    private Button btn_continue;

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
        void onClickFormulaire(int id, String userName);
    }

    private FormulaireMainFragment.FormulaireFragmentCallBack callback;
    public void setcallback(MainActivity callback){
        this.callback = (FormulaireFragmentCallBack) callback;
    }
    //endregion


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        vFormulaire = initFragment(vFormulaire);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vFormulaire = inflater.inflate(R.layout.fragment_main_formulaire, container, false);

        return vFormulaire;
    }

    private View initFragment(View v){
        et_lastName = (EditText) v.findViewById(R.id.et_formulaire_Last_name);
        et_firstName = (EditText) v.findViewById(R.id.et_formulaire_first_name);
        et_userName = (EditText) v.findViewById(R.id.et_formulaire_user_name);
        et_email = (EditText) v.findViewById(R.id.et_formulaire_email);
        et_password = (EditText) v.findViewById(R.id.et_formulaire_password);

        btn_confirm_info = (Button) v.findViewById(R.id.btn_formulaire_confirm);
        btn_continue = (Button) v.findViewById(R.id.btn_formulaire_continue);

        //btn_continue.setEnabled(false);
        //btn_confirm_info.setEnabled(false);

        btn_continue.setOnClickListener(this);
        btn_confirm_info.setOnClickListener(this);

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


        return v;
    }

    private User newUser(){
        String lastName = et_lastName.getText().toString().trim();
        String firstName = et_firstName.getText().toString().trim();
        String userName = et_userName.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        User u = new User(lastName,firstName,userName,email,password);
        return u;
    }

    @Override
    public void onClick(View view) {
        newGreatUser = newUser();
        sendCallBack(view.getId(),newGreatUser);
    }

    private void sendCallBack(int id,User user) {
        if(callback!=null){
            String userName = user.getUserName();
            callback.onClickFormulaire(id, userName);
        }
    }

}
