package com.formation.appli.bruxellesparcourbd.ui.FresqueBD;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.formation.appli.bruxellesparcourbd.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FresqueBDshowFragment extends Fragment {


    public FresqueBDshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fresque_bdshow, container, false);
    }

}
