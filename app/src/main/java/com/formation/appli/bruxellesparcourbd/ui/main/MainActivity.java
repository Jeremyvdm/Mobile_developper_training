package com.formation.appli.bruxellesparcourbd.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.formation.appli.bruxellesparcourbd.DB.UserDAO;
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



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void onCLickChoice(int id, String userName) {
        switch (id){
            case R.id.btn_acceuil_connection:
                connection();
                break;
            case R.id.btn_acceuil_new_user:
                loadFormulaire();
                break;
            case R.id.btn_acceuil_continue:
                Start_user_activity(userName);
                break;
        }
    }

    @Override
    public void onClickFormulaire(int id, String userName) {
        switch (id){
            case R.id.btn_formulaire_confirm:
                onConfirm();
                break;
            case R.id.btn_formulaire_continue:
                Start_user_activity(userName);
                break;
        }
    }

    public void loadFormulaire(){
        FormulaireMainFragment formulaireFragment = FormulaireMainFragment.getInstance();
        formulaireFragment.setcallback(this);
        loadFragment(R.id.fl_main_frame,formulaireFragment);
    }

    //region firebase new user connection
    public void connection(){

        EditText et_email = (EditText) findViewById(R.id.et_acceuil_email);
        EditText et_password = (EditText) findViewById(R.id.et_acceuil_Password);
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, R.string.firebase_auth_failed,
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        else{
                            Toast.makeText(MainActivity.this, R.string.firebase_auth_succeed,
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                    }
                });
    }

    public void onConfirm() {
        EditText et_email = (EditText) findViewById(R.id.et_formulaire_email);
        EditText et_password = (EditText) findViewById(R.id.et_formulaire_password);
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "createdUserWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, R.string.firebase_auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, R.string.firebase_auth_creation_succeed,
                                    Toast.LENGTH_SHORT).show();
                            sendEmailVerification();
                            int id = R.id.btn_formulaire_continue;
                            Button btn_continue = (Button) findViewById(R.id.btn_formulaire_continue);
                            btn_continue.setEnabled(true);

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

    private void Start_user_activity(String userName){
        Intent userIntent = new Intent(this,UserActivity.class);
        userIntent.putExtra(UserDAO.COLUMN_USER_NAME,userName);
        startActivity(userIntent);
    }

    //region fireBase
    private void sendEmailVerification() {
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button

                        if (task.isSuccessful()) {

                            Toast.makeText(MainActivity.this,
                                    getString(R.string.FireBaseVerification_email_sent) + user.getEmail() + "\n please validate your account",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(MainActivity.this,
                                    R.string.FireBasefailed_verification_email_sending,
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    private void updateUI( FirebaseUser currentUser) {

        TextView tv_log_Status = (TextView) findViewById(R.id.tv_acceuil_log_Status);
        TextView tv_log_Detail = (TextView) findViewById(R.id.tv_acceuil_log_Detail);
        Button btn_continue = (Button) findViewById(R.id.btn_acceuil_continue);


        if (currentUser != null) {
            tv_log_Status.setText(getString(R.string.emailpassword_status_fmt,
                    currentUser.getEmail(), currentUser.isEmailVerified()));
            tv_log_Detail.setText(getString(R.string.firebase_status_fmt, currentUser.getUid()));
            btn_continue.setEnabled(currentUser.isEmailVerified());

        } else {
            tv_log_Status.setText(R.string.signed_out);
            tv_log_Detail.setText(null);

        }



    }


    //endregion
}
