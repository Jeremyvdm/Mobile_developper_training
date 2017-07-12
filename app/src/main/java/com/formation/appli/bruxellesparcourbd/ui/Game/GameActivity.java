package com.formation.appli.bruxellesparcourbd.ui.Game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.model.ParcourtBD;
import com.formation.appli.bruxellesparcourbd.tools.LocalisationGPS;
import com.formation.appli.bruxellesparcourbd.ui.Victory.EndGameActivity;
import com.formation.appli.bruxellesparcourbd.ui.parcourt.ParcourtActivity;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements PlayFragemnt.playFragemntCallback, ResultFragment.ResultFragmentCallback, LocalisationGPS.LocalisationGPSCallBack{

    private Bundle extra;

    private int numeroParcourt;

    private ParcourtBD parcourtBDchoisis;

    private ArrayList<FresqueBD> parcourtBdChoisisArray;

    private FresqueBD fresqueBDPlay;

    private ImageView iv_fresque_activity_num_parcourt;

    public final static String FRESQUE_BD_PLAY = "Fresque_bd_play";
    public final static String FRESQUE_RESULT = "Fresque_bd_result";

    private int fresqueNumber;
    private int maxFresque;

    private double maPostionLatitude;
    private double maPositonLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresque);
        initView();
    }



    private void initView(){
        iv_fresque_activity_num_parcourt = (ImageView) findViewById(R.id.iv_parcourt_number_image_fresq_activity);
        extra = getIntent().getExtras();
        parcourtBDchoisis = extra.getParcelable(ParcourtActivity.PARCOURT_BD_CHOISIS);
        numeroParcourt = extra.getInt(ParcourtActivity.PARCOURT_BD_CHOISIS);

        switch (numeroParcourt) {
            case 1:
                iv_fresque_activity_num_parcourt.setImageResource(R.drawable.parcour_n_1);
                break;
            case 2:
                iv_fresque_activity_num_parcourt.setImageResource(R.drawable.parcour_n_2);
                break;
            case 3:
                iv_fresque_activity_num_parcourt.setImageResource(R.drawable.parcour_n_3);
                break;
            case 4:
                iv_fresque_activity_num_parcourt.setImageResource(R.drawable.parcour_n_4);
                break;
            case 5:
                iv_fresque_activity_num_parcourt.setImageResource(R.drawable.parcour_n_5);
                break;
            default:
                iv_fresque_activity_num_parcourt.setImageResource(R.drawable.parcout_bd_global);
                break;
        }
        fresqueNumber = 0;
        parcourtBdChoisisArray = parcourtBDchoisis.getParcourtFresqueBD();
        fresqueBDPlay = parcourtBdChoisisArray.get(fresqueNumber);
        Bundle playBd = new Bundle();
        playBd.putParcelable(FRESQUE_BD_PLAY,fresqueBDPlay);
        maxFresque = parcourtBDchoisis.getParcourtFresqueBD().size();
        PlayFragemnt playFragemnt = new PlayFragemnt();
        playFragemnt.setCallback(this);
        loadFragment(R.id.fr_fresque_activity_frame,playFragemnt);

    }

    private void loadFragment(int id, Fragment fragment){
        FragmentManager fragman = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragman.beginTransaction();
        if(fragment != null){
            fragTrans.replace(id, fragment);
            fragTrans.addToBackStack(null);
        }else{
            fragTrans.add(id, fragment);
        }
        fragTrans.commit();
    }

    @Override
    public void onClickPlay(int id) {
        switch (id){
            case R.id.btn_Play_fresque_det_arriver:
                arriverALaFresque();
                break;
            case R.id.btn_Play_fresque_det_next_fresque:
                passerLaFresque();
                break;
        }
    }

    @Override
    public void onClickResult() {
        fresqueNumber+=1;
        if (maxFresque > fresqueNumber){
            Bundle resultBd = new Bundle();
            resultBd.putParcelable(FRESQUE_BD_PLAY,fresqueBDPlay);
            maxFresque = parcourtBDchoisis.getParcourtFresqueBD().size();
            PlayFragemnt playFragemnt = new PlayFragemnt();
            playFragemnt.setCallback(this);
            loadFragment(R.id.fr_fresque_activity_frame,playFragemnt);
        }else{
            Intent endGame = new Intent(this, EndGameActivity.class);
            startActivity(endGame);
        }

    }

    private void arriverALaFresque(){
        double latitude = fresqueBDPlay.getCoordonees().getLatitude();
        double longitude = fresqueBDPlay.getCoordonees().getLongitude();
        checkPositionPlay(latitude,longitude);
        Bundle resultBundle = new Bundle();
        if(checkPositionPlay(latitude,longitude)){
            resultBundle.putString(FRESQUE_RESULT,"bravo");
        }else{
            resultBundle.putString(FRESQUE_RESULT,"perdu");
        }
        ResultFragment resultFragment = new ResultFragment();
        resultFragment.setCallback(this);
        loadFragment(R.id.fr_fresque_activity_frame,resultFragment);
    }

    private void passerLaFresque(){
        fresqueNumber+=1;
        if (maxFresque > fresqueNumber){
            Bundle resultBd = new Bundle();
            resultBd.putParcelable(FRESQUE_BD_PLAY,fresqueBDPlay);
            maxFresque = parcourtBDchoisis.getParcourtFresqueBD().size();
            PlayFragemnt playFragemnt = new PlayFragemnt();
            playFragemnt.setCallback(this);
            loadFragment(R.id.fr_fresque_activity_frame,playFragemnt);
        }else{
            Intent endGame = new Intent(this, EndGameActivity.class);
            startActivity(endGame);
        }
    }

    private Boolean checkPositionPlay(double latitude, double longitude){
        loca();
        if(latitude-maPostionLatitude < 2 && longitude - maPositonLongitude < 2){
            return true;
        }else{
            return false;
        }
    }

    private void loca() {
        LocalisationGPS gps = new LocalisationGPS();
        gps.setCallback(this);
        gps.demande(this);
    }

    @Override
    public void localiser(double latitude, double longitude) {
        maPostionLatitude = latitude;
        maPositonLongitude = longitude;
    }
}
