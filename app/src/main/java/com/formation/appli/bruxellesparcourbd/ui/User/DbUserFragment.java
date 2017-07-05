package com.formation.appli.bruxellesparcourbd.ui.User;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.formation.appli.bruxellesparcourbd.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DbUserFragment extends Fragment {


    public DbUserFragment() {
        // Required empty public constructor
    }

    //region Singelton
    private static DbUserFragment instance;

    public static DbUserFragment getInstance(){
        if(instance == null){
            instance = new DbUserFragment();
        }
        return instance;
    }

    //endregion

    //region Communication
    public interface DbUserFragmentCallBack{
        void DBActivity(int debut, int fin);
    }

    private DbUserFragmentCallBack callback;
    public void setcallback(UserActivity callback){
        this.callback = (DbUserFragmentCallBack) callback;
    }
    //endregion


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_db, container, false);
    }

}
