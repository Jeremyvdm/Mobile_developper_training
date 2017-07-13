package com.formation.appli.bruxellesparcourbd.ui.Game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.formation.appli.bruxellesparcourbd.R;


public class ResultFragment extends Fragment implements View.OnClickListener{

    private ImageView ivResultFresque;
    private TextView tvResultFresque;
    private Button  btnResultFresque;
    private Bundle bundleResult;
    private String result;

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
        View v =  inflater.inflate(R.layout.fragment_result, container, false);
        v = initFragment(v);
        return v;
    }

    private View initFragment(View v){
        bundleResult = this.getArguments();

        ivResultFresque = (ImageView) v.findViewById(R.id.iv_play_result_frag);
        tvResultFresque = (TextView) v.findViewById(R.id.tv_play_result_frag);
        btnResultFresque = (Button) v.findViewById(R.id.btn_play_result_frag_suivant);

        result = bundleResult.getString(GameActivity.FRESQUE_RESULT);
        if(result.equals("bravo")){
            ivResultFresque.setImageResource(R.drawable.bravo);
            tvResultFresque.setText(R.string.tv_game_result_frag_bravo);
        }else{
            ivResultFresque.setImageResource(R.drawable.rate);
            tvResultFresque.setText(R.string.tv_game_result_frag_perdu);
        }
        ivResultFresque.setAdjustViewBounds(true);
        btnResultFresque.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View view) {
        if(callback!=null){
            callback.onClickResult();
        }
    }


}
