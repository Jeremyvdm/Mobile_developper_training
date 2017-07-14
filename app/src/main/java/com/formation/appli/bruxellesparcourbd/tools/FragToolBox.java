package com.formation.appli.bruxellesparcourbd.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Jeremyvdm on 13/07/2017.
 */
// Fonction qui va servir à charger mes fragments
public class FragToolBox {

    public static void loadFragment(FragmentActivity activity, int id, Fragment fragment){
        FragmentManager fragman = activity.getSupportFragmentManager();
        FragmentTransaction fragTrans = fragman.beginTransaction();
        if(fragment != null){
            fragTrans.replace(id, fragment);
            fragTrans.addToBackStack(null);
        }else{
            fragTrans.add(id, fragment);
        }
        fragTrans.commit();
    }
}
