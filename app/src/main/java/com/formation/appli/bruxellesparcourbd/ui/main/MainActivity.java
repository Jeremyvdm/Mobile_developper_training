package com.formation.appli.bruxellesparcourbd.ui.main;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.formation.appli.bruxellesparcourbd.DB.UserDAO;
import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.ui.User.UserActivity;

public class MainActivity extends AppCompatActivity implements AcceuilMainFragment.AcceuilFragmentCallBack, FormulaireMainFragment.FormulaireFragmentCallBack{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
    }

    private void initFragment(){
        FragmentManager fragman = getSupportFragmentManager();

        FragmentTransaction fragTrans = fragman.beginTransaction();

        AcceuilMainFragment fragment = AcceuilMainFragment.getInstance();
        fragment.setcallback(this);

        fragTrans.add(R.id.fl_main_frame, fragment);
        fragTrans.commit();
    }


    @Override
    public void onLoging(String userName, String Password) {
        Start_user_activity(userName);
    }

    @Override
    public void onConfirm(String userName) {
        Start_user_activity(userName);
    }

    private void Start_user_activity(String userName){
        Intent userIntent = new Intent(this,UserActivity.class);
        userIntent.putExtra(UserDAO.COLUMN_USER_NAME,userName);
        startActivity(userIntent);
    }
}
