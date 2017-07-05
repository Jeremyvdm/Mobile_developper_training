package com.formation.appli.bruxellesparcourbd.ui.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.formation.appli.bruxellesparcourbd.DB.UserDAO;
import com.formation.appli.bruxellesparcourbd.R;


public class ChoiceUserFragment extends Fragment implements View.OnClickListener{

    private Bundle newExtra;
    private TextView tv_user_frag;
    private Button btn_choiceParcourt;
    private Button btn_accesdb;
    private Button btn_parcourtGeneral;

    public ChoiceUserFragment() {
        // Required empty public constructor
    }

    //region Singelton
    private static ChoiceUserFragment instance;

    public static ChoiceUserFragment getInstance(){
        if(instance == null){
            instance = new ChoiceUserFragment();
        }
        return instance;
    }

    //endregion

    //region Communication
    public interface ChoiceUserFragmentCallBack{
        void onCLick(int id);
    }

    private ChoiceUserFragmentCallBack callback;
    public void setcallback(UserActivity callback){
        this.callback = (ChoiceUserFragmentCallBack) callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_choice, container, false);

        v = initView(v);

        return v;
    }

    private View initView(View v){
        newExtra = new Bundle();

        tv_user_frag = (TextView) v.findViewById(R.id.tv_choice_user_fragment);

        btn_choiceParcourt = (Button) v.findViewById(R.id.btn_choice_user_choix_parcourt);
        btn_accesdb = (Button) v.findViewById(R.id.btn_choice_user_acces_db);
        btn_parcourtGeneral = (Button) v.findViewById(R.id.btn_choice_user_parcourt_general);

        String name = newExtra.getString(UserDAO.COLUMN_USER_NAME);
        tv_user_frag.setText(String.format(getString(R.string.user_fragment),name));

        btn_choiceParcourt.setOnClickListener(this);
        btn_accesdb.setOnClickListener(this);
        btn_parcourtGeneral.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        sendCallBack(view.getId());
    }

    private void sendCallBack(int id){
        if (callback != null){
            callback.onCLick(id);
        }
    }
}
