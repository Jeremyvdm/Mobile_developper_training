package com.formation.appli.bruxellesparcourbd.DB;

import android.util.Log;

import com.formation.appli.bruxellesparcourbd.model.ParcoursDbTitre;
import com.formation.appli.bruxellesparcourbd.model.User;
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

public class ParcoursBDDAO {
    //region nom table et colonne
    public final static String PARCOURTBDTABLE = "parcours_bd";
    public final static String COLUMN_PARCOURSBD_ID ="parcours_BD_ID";
    public final static String COLUMN_PARCOURSBD_TITRE = "parcours_bd_titre";
    public final static String COLUMN_PARCOURSBD_TITRE_FRESQUE = "parcours_bd_titre_fresque";
    public static final String TAG = "error db";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(PARCOURTBDTABLE);

    public void addParcoursToDatrabase(ParcoursDbTitre parcoursDbTitre){
        myRef.child(COLUMN_PARCOURSBD_ID).setValue(parcoursDbTitre);
    }

    public void updateDataFromDatabase(ParcoursDbTitre parcoursDbTitre){
        String key = myRef.child(PARCOURTBDTABLE).push().getKey();
        Map<String, Object> postValues = parcoursDbTitre.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/"+PARCOURTBDTABLE+"/" + key, postValues);
        childUpdates.put("/user-posts/" + COLUMN_PARCOURSBD_ID + "/" + key, postValues);

        myRef.updateChildren(childUpdates);
    }

    public void deleteParcourFromDataBase(){

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
