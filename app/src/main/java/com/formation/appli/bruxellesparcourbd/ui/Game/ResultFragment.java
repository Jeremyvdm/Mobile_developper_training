package com.formation.appli.bruxellesparcourbd.ui.Game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.formation.appli.bruxellesparcourbd.R;


public class ResultFragment extends Fragment implements View.OnClickListener{

    public ResultFragment() {
        // Required empty public constructor
    }

    //region Singleton
    private static ResultFragment instance;

    public static ResultFragment getInstance() {
        if (instance == null) {
            instance = new ResultFragment();
        }
        return instance;
    }


    //endregion

    //region Communication
    public interface ResultFragmentCallback {
        void onClickResult();
    }

    private ResultFragmentCallback callback;

    public void setCallback(ResultFragmentCallback callback) {
        this.callback = callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onClick(View view) {
        if(callback!=null){
            callback.onClickResult();
        }
    }


}
