package com.formation.appli.bruxellesparcourbd.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.ui.User.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements AcceuilMainFragment.AcceuilFragmentCallBack, FormulaireMainFragment.FormulaireFragmentCallBack{

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button btn_conexion;
    private Button et_main_password;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFirebase();
        initFragment();
    }
    //region on start on stop firebase custom made
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    //endregion

    private void initFragment(){
        AcceuilMainFragment fragment = AcceuilMainFragment.getInstance();
        fragment.setcallback(this);
        loadFragment(R.id.fl_main_frame, fragment);
    }


    @Override
    public void onCLickChoice(int id,String email, String password) {
        switch (id){
            case R.id.btn_acceuil_connection:
                connection(email,password);
                break;
            case R.id.btn_acceuil_new_user:
                loadFormulaire();
                break;
            case R.id.btn_acceuil_continue:
                Start_user_activity();
                break;
        }
    }

    @Override
    public void onClickFormulaire(int id, String email, String password) {
        switch (id){
            case R.id.btn_formulaire_confirm:
                onConfirm(email,password);
                break;
            case R.id.btn_formulaire_continue:
                Start_user_activity();
        }
    }

    public void loadFormulaire(){
        FormulaireMainFragment formulaireFragment = FormulaireMainFragment.getInstance();
        formulaireFragment.setcallback(this);
        loadFragment(R.id.fl_main_frame,formulaireFragment);
    }

    //region firebase new user connection
    public void connection(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, R.string.firebase_auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, R.string.firebase_auth_succeed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onConfirm(String email,String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, R.string.firebase_auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, R.string.firebase_auth_creation_succeed,
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    //endregion

    private void loadFragment(int id, Fragment fragment){
        FragmentManager fragman = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragman.beginTransaction();
        if(fragment != null){
            fragTrans.replace(id, fragment);
        }else{
            fragTrans.add(id, fragment);
        }
        fragTrans.commit();
    }

    private void Start_user_activity(){
        Intent userIntent = new Intent(this,UserActivity.class);
        startActivity(userIntent);
    }

    //region fireBase
    private void initFirebase(){
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }


    private void FireBaseGettingIformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }
    //endregion
}
