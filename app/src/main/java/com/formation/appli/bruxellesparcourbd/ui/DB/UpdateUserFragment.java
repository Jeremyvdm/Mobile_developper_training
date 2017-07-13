package com.formation.appli.bruxellesparcourbd.ui.DB;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.User;


public class UpdateUserFragment extends Fragment {


    public UpdateUserFragment() {
        // Required empty public constructor
    }
    private EditText et_db_update_last_name;
    private EditText et_db_update_first_name;
    private EditText et_db_update_user_name;
    private EditText et_db_update_email;
    private EditText et_db_update_password;

    private Button btn_db_confirm_info;

    //region Singleton
    private static UpdateUserFragment instance;

    public static UpdateUserFragment getInstance() {
        if (instance == null) {
            instance = new UpdateUserFragment();
        }
        return instance;
    }


    //endregion

    //region Communication
    public interface UpdateUserFragmentCallback {
        void onClickConfirmUpdate(User user);
    }

    private UpdateUserFragmentCallback callback;

    public void setCallback(UpdateUserFragmentCallback callback) {
        this.callback = callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_update_user, container, false);
        v = initFragment(v);
        return v;
    }

    private View initFragment(View v){
        et_db_update_last_name = (EditText) v.findViewById(R.id.et_database_upt_frag_Last_name);
        et_db_update_first_name = (EditText) v.findViewById(R.id.et_database_upt_frag_first_name);
        et_db_update_user_name = (EditText) v.findViewById(R.id.et_database_upt_frag_user_name);
        et_db_update_email = (EditText) v.findViewById(R.id.et_database_upt_frag_email);
        et_db_update_password = (EditText) v.findViewById(R.id.et_database_upt_frag_password);

        btn_db_confirm_info = (Button) v.findViewById(R.id.btn_database_upt_frag_confirm);
        String lastName = et_db_update_last_name.getText().toString().trim();
        String firstName = et_db_update_first_name.getText().toString().trim();
        String userName = et_db_update_user_name.getText().toString().trim();
        String email = et_db_update_email.getText().toString().trim();
        String newPassword = et_db_update_password.getText().toString().trim();
        final User user = new User(lastName,firstName,userName,email,newPassword);

        btn_db_confirm_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCallBack(user);
            }
        });



        return v;
    }
    private void sendCallBack(User user) {
        if (callback != null) {
            callback.onClickConfirmUpdate(user);
        }
    }
}
