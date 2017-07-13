package com.formation.appli.bruxellesparcourbd.ui.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.formation.appli.bruxellesparcourbd.DB.UserDAO;
import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.tools.FragToolBox;
import com.formation.appli.bruxellesparcourbd.ui.parcours.ParcoursActivity;

public class UserActivity extends AppCompatActivity implements ChoiceUserFragment.ChoiceUserFragmentCallBack, ParcoursUserFragment.ParcoursUserFragmentCallBack {
    public static final String NUMERODEPARCOURS = "num_parc_choisi";
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
        FragToolBox.loadFragment(this,R.id.fl_user_frame, fragment);
    }




    @Override
    public void onCLick(int id) {
        switch (id){
            case R.id.btn_choice_user_choix_parcours:
                loadParcourtFragment();
                break;
            case R.id.btn_choice_user_acces_db:
                loadDbFragment();
                break;
            case R.id.btn_choice_user_parcours_general:
                loadParcourtActivity();
                break;
        }
    }

    private void loadParcourtFragment(){
        ParcoursUserFragment parUsFrag = new ParcoursUserFragment();
        parUsFrag.setcallback(this);
        FragToolBox.loadFragment(this,R.id.fl_user_frame, parUsFrag);
    }

    private void loadDbFragment(){
        DbUserFragment dbUsFrag = new DbUserFragment();
        dbUsFrag.setcallback(this);
        FragToolBox.loadFragment(this,R.id.fl_user_frame, dbUsFrag);
    }

    private void loadParcourtActivity(){
        int parcourtNumber = 6;
        Intent parcourtIntent = new Intent(this, ParcoursActivity.class);
        parcourtIntent.putExtra(NUMERODEPARCOURS,parcourtNumber);
        startActivity(parcourtIntent);
    }

    @Override
    public void parcourtActivity(int parcourtNumber) {
        Intent parcourtIntent = new Intent(this, ParcoursActivity.class);
        parcourtIntent.putExtra(NUMERODEPARCOURS,parcourtNumber);
        startActivity(parcourtIntent);
    }



}

