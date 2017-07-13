package com.formation.appli.bruxellesparcourbd.DB;

import android.util.Log;

import com.formation.appli.bruxellesparcourbd.model.User;
import com.formation.appli.bruxellesparcourbd.ui.main.MainActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeremyvdm on 02/07/2017.
 */

public class UserDAO {


    //region table and column name
    public static final String TABLE_USER = "user";
    public static final String COLUMN_USERID = "_userId";
    public static final String COLUMN_LAST_NAME = "last name";
    public static final String COLUMN_FIRST_NAME = "first name";
    public static final String COLUMN_USER_NAME = "user name";
    public static final String COLUMN_EMAIL = "email";
    //endregion

    public static final String TAG = "error db";

    public static FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public static DatabaseReference myRef;
    private FirebaseUser currentUser = MainActivity.mAuth.getCurrentUser();



    public void addUserTodatabase(User user){
        String lastName = user.getLastName();
        String firstName = user.getFirstName();
        String userName = user.getUserName();
        String email = user.getEmail();
        myRef = mDatabase.getInstance().getReference().child(TABLE_USER +"/"+currentUser.getUid()).child(COLUMN_LAST_NAME);
        myRef.setValue(lastName);
        myRef = mDatabase.getInstance().getReference().child(TABLE_USER + "/" + currentUser.getUid()).child(COLUMN_FIRST_NAME);
        myRef.setValue(firstName);
        myRef = mDatabase.getInstance().getReference().child(TABLE_USER + "/" + currentUser.getUid()).child(COLUMN_USER_NAME);
        myRef.setValue(userName);
        myRef = mDatabase.getInstance().getReference().child(TABLE_USER + "/" + currentUser.getUid()).child(COLUMN_EMAIL);
        myRef.setValue(email);
    }

    public void UpdateUserInDataBase(User user){
        String key = MainActivity.mAuth.getCurrentUser().getUid();
        Map<String, Object> postValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/"+TABLE_USER+"/" + key, postValues);
        childUpdates.put("/"+TABLE_USER+"/" + key, postValues);
        childUpdates.put("/"+TABLE_USER+"/" + key, postValues);
        childUpdates.put("/"+TABLE_USER+"/" + key, postValues);
        childUpdates.put("/"+TABLE_USER+"/" + key, postValues);

        myRef.updateChildren(childUpdates);
    }



    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            User user = dataSnapshot.getValue(User.class);
            // ...
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };


}
