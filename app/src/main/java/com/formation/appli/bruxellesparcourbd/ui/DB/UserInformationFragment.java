package com.formation.appli.bruxellesparcourbd.ui.DB;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.User;


public class UserInformationFragment extends Fragment {

    public UserInformationFragment() {
        // Required empty public constructor
    }

    private Bundle infoBundle;
    private TextView tv_db_info_last_name;
    private TextView tv_db_info_first_name;
    private TextView tv_db_info_user_name;
    private TextView tv_db_info_email;
    private TextView tv_db_info_password;

    private Button btn_db_update_info;



    //region Singleton
    private static UserInformationFragment instance;

    public static UserInformationFragment getInstance() {
        if (instance == null) {
            instance = new UserInformationFragment();
        }
        return instance;
    }


    //endregion

    //region Communication
    public interface UserInformationFragmentCallback {
        void onClickUpdate();
    }

    private UserInformationFragmentCallback callback;

    public void setCallback(UserInformationFragmentCallback callback) {
        this.callback = callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user_information, container, false);
        v = initFragment(v);
        return v;
    }

    private View initFragment(View v){
        infoBundle = this.getArguments();
        User currentUser = infoBundle.getParcelable(DatabaseActivity.CURRENT_USER);
        tv_db_info_last_name = (TextView) v.findViewById(R.id.tv_database_info_frag_Last_name);
        tv_db_info_first_name = (TextView) v.findViewById(R.id.tv_database_info_frag_first_name);
        tv_db_info_user_name = (TextView) v.findViewById(R.id.tv_database_info_frag_user_name);
        tv_db_info_email = (TextView) v.findViewById(R.id.tv_database_info_frag_email);
        tv_db_info_last_name.setText(currentUser.getLastName());
        tv_db_info_first_name.setText(currentUser.getFirstName());
        tv_db_info_user_name.setText(currentUser.getUserName());
        tv_db_info_email.setText(currentUser.getEmail());

        btn_db_update_info = (Button) v.findViewById(R.id.btn_database_info_frag_confirm);
        btn_db_update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCallBack();
            }
        });
        return v;
    }

    private void sendCallBack(){
        if(callback!=null){
            callback.onClickUpdate();
        }
    }

}
