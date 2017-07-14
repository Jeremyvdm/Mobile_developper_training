package com.formation.appli.bruxellesparcourbd.ui.DB;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.formation.appli.bruxellesparcourbd.DB.UserDAO;
import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.User;
import com.formation.appli.bruxellesparcourbd.tools.FragToolBox;
import com.formation.appli.bruxellesparcourbd.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseActivity extends AppCompatActivity implements UserInformationFragment.UserInformationFragmentCallback, UpdateUserFragment.UpdateUserFragmentCallback{

    private static final String TAG = "update_password";
    public static final String CURRENT_USER = "cuurentUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        iniFragment();
    }

    private void iniFragment(){
        FirebaseDatabase databse = FirebaseDatabase.getInstance();
        final String mUserId = MainActivity.mAuth.getCurrentUser().getUid();
        DatabaseReference mRef = databse.getReference("parcourtbd/" + UserDAO.TABLE_USER);
        final Bundle  infoBundle = new Bundle();


        //Single event listener
        mRef.child(mUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User currentUser = dataSnapshot.getValue(User.class);
                infoBundle.putParcelable(CURRENT_USER, currentUser);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(getApplicationContext(),"failed to read value",Toast.LENGTH_SHORT).show();
                // ...
            }
        });

        UserInformationFragment informationFragment = UserInformationFragment.getInstance();
        informationFragment.setCallback(this);
        informationFragment.setArguments(infoBundle);
        FragToolBox.loadFragment(this,R.id.fl_database_frame, informationFragment);
    }
    private User retriveUserData(DataSnapshot dataSnapshot) {
        User userInformation = new User();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            userInformation = ds.getValue(User.class);
        }
        return userInformation;
    }


    @Override
    public void onClickConfirmUpdate(User user) {
        UserDAO userDAO = new UserDAO();
        FirebaseUser userFirebase = MainActivity.mAuth.getCurrentUser();
        userFirebase.updatePassword(user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                        }
                    }
                });
        userDAO.UpdateUserInDataBase(user);
    }

    @Override
    public void onClickUpdate() {
        UpdateUserFragment upDateUserFragment = new UpdateUserFragment();
        upDateUserFragment.setCallback(this);
        FragToolBox.loadFragment(this,R.id.fl_database_frame,upDateUserFragment);
    }
}
