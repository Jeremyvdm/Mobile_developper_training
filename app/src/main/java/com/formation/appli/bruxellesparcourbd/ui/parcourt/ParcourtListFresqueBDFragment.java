package com.formation.appli.bruxellesparcourbd.ui.parcourt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.ParcourtBD;
import com.formation.appli.bruxellesparcourbd.ui.User.UserActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParcourtListFresqueBDFragment extends Fragment {

    private ParcourtBD parcourtBD;
    private int numéroParcourChoisi;
    Bundle bdBundle;


    public ParcourtListFresqueBDFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_parcourt_list_fresque_bd, container, false);
        initParcourt();
        initFragment();
        return v;
    }

    private void initFragment(){

    }

    private void initParcourt(){
        bdBundle = this.getArguments();
        parcourtBD = bdBundle.getParcelable(ParcourtActivity.PARCOURT_BD_CHOISIS);
        numéroParcourChoisi = bdBundle.getInt(UserActivity.NUMERODEPARCOURT);
    }

}
