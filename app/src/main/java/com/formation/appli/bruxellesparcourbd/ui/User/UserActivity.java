package com.formation.appli.bruxellesparcourbd.ui.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.formation.appli.bruxellesparcourbd.DB.UserDAO;
import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.ui.parcourt.ParcourtActivity;

public class UserActivity extends AppCompatActivity implements ChoiceUserFragment.ChoiceUserFragmentCallBack, ParcourtUserFragment.ParcourtUserFragmentCallBack{
    public static final String PARCOURTDEBUT = "debut";
    public static final String PARCOURTFIN = "fin";
    public static final String NUMERODEPARCOURT = "num_parc_choisi";
    private int debut;
    private int fin;
    Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        extra = getIntent().getExtras();
        initFragment();
    }

    private void initFragment(){
        String name = extra.getString(UserDAO.COLUMN_USER_NAME);
        Bundle newextra = new Bundle();
        newextra.putString(UserDAO.COLUMN_USER_NAME,name);
        ChoiceUserFragment fragment = ChoiceUserFragment.getInstance();
        fragment.setcallback(this);
        fragment.setArguments(newextra);
        loadFragment(R.id.fl_user_frame, fragment);
    }

    private void loadFragment(int id, Fragment fragment){
        FragmentManager fragman = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragman.beginTransaction();
        if(fragment != null){
            fragTrans.replace(id, fragment);
        }else{
            fragTrans.add(id, fragment);
        }
        fragTrans.commit();
    }
    @Override
    public void onCLick(int id) {
        switch (id){
            case R.id.btn_choice_user_choix_parcourt:
                loadParcourtFragment();
                break;
            case R.id.btn_choice_user_acces_db:
                loadDbFragment();
                break;
            case R.id.btn_choice_user_parcourt_general:
                loadParcourtActivity();
                break;
        }
    }

    private void loadParcourtFragment(){
        ParcourtUserFragment parUsFrag = new ParcourtUserFragment();
        parUsFrag.setcallback(this);
        loadFragment(R.id.fl_user_frame, parUsFrag);
    }

    private void loadDbFragment(){
        DbUserFragment dbUsFrag = new DbUserFragment();
        dbUsFrag.setcallback(this);
        loadFragment(R.id.fl_user_frame, dbUsFrag);
    }

    private void loadParcourtActivity(){
        int parcourtNumber = 6;
        initParcourtJson(parcourtNumber);
        Intent parcourtIntent = new Intent(this, ParcourtActivity.class);
        parcourtIntent.putExtra(NUMERODEPARCOURT,parcourtNumber);
        startActivity(parcourtIntent);
    }

    @Override
    public void parcourtActivity(int parcourtNumber) {
        initParcourtJson(parcourtNumber);
        Intent parcourtIntent = new Intent(this, ParcourtActivity.class);
        parcourtIntent.putExtra(NUMERODEPARCOURT,parcourtNumber);
        parcourtIntent.putExtra(PARCOURTDEBUT,debut);
        parcourtIntent.putExtra(PARCOURTFIN,fin);
        startActivity(parcourtIntent);
    }

    private void initParcourtJson(int parcourtNumber){
        switch (parcourtNumber){
            case 1:
                debut = 0;
                fin = 10;
                break;
            case 2:
                debut = 10;
                fin = 20;
                break;
            case 3:
                debut = 20;
                fin = 30;
                break;
            case 4:
                debut = 30;
                fin = 40;
                break;
            case 5:
                debut = 40;
                fin = 52;
                break;
            case 6:
                debut = 0;
                fin = 52;
                break;
        }
    }


}

