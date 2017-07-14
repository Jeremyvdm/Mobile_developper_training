package com.formation.appli.bruxellesparcourbd.ui.Game;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.model.ParcoursBD;
import com.formation.appli.bruxellesparcourbd.tools.FragToolBox;
import com.formation.appli.bruxellesparcourbd.tools.LocalisationGPS;
import com.formation.appli.bruxellesparcourbd.ui.User.UserActivity;
import com.formation.appli.bruxellesparcourbd.ui.Victory.EndGameActivity;
import com.formation.appli.bruxellesparcourbd.ui.parcours.ParcoursActivity;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements PlayFragemnt.playFragemntCallback, ResultFragment.ResultFragmentCallback, LocalisationGPS.LocalisationGPSCallBack{

    private Bundle extra;

    private int numeroParcours;

    private ParcoursBD parcoursBDchoisis;

    private ArrayList<FresqueBD> parcoursBdChoisisArray;

    private FresqueBD fresqueBDPlay;

    private ImageView iv_fresque_activity_num_parcours;

    private static final int MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1;

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
        requestPermission();
        initView();
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
        }
    }



    private void initView(){
        iv_fresque_activity_num_parcours = (ImageView) findViewById(R.id.iv_parcourt_number_image_fresq_activity);
        extra = getIntent().getExtras();
        parcoursBDchoisis = extra.getParcelable(ParcoursActivity.PARCOURS_BD_CHOISIS);
        numeroParcours = extra.getInt(UserActivity.NUMERODEPARCOURS);

        switch (numeroParcours) {
            case 1:
                iv_fresque_activity_num_parcours.setImageResource(R.drawable.parcour_n_1);
                break;
            case 2:
                iv_fresque_activity_num_parcours.setImageResource(R.drawable.parcour_n_2);
                break;
            case 3:
                iv_fresque_activity_num_parcours.setImageResource(R.drawable.parcour_n_3);
                break;
            case 4:
                iv_fresque_activity_num_parcours.setImageResource(R.drawable.parcour_n_4);
                break;
            case 5:
                iv_fresque_activity_num_parcours.setImageResource(R.drawable.parcour_n_5);
                break;
            default:
                iv_fresque_activity_num_parcours.setImageResource(R.drawable.parcout_bd_global);
                break;
        }
        fresqueNumber = 0;
        parcoursBdChoisisArray = parcoursBDchoisis.getParcoursFresqueBD();
        fresqueBDPlay = parcoursBdChoisisArray.get(fresqueNumber);
        Bundle playBd = new Bundle();
        playBd.putParcelable(FRESQUE_BD_PLAY,fresqueBDPlay);
        maxFresque = parcoursBDchoisis.getParcoursFresqueBD().size();
        PlayFragemnt playFragemnt = new PlayFragemnt();
        playFragemnt.setArguments(playBd);
        playFragemnt.setCallback(this);
        FragToolBox.loadFragment(this, R.id.fr_fresque_activity_frame,playFragemnt);

    }



    @Override
    public void onClickPlay(int id) {
        switch (id){
            case R.id.btn_Play_fresque_det_arriver:
                arriverALaFresque();
                break;
            case R.id.btn_Play_fresque_det_next_fresque:
                fresqueSuivante();
                break;
            case R.id.btn_Play_fresque_det_show_map:
                showmap();
                break;
        }
    }
    private void showmap(){
        Bundle mapBundle = new Bundle();
        mapBundle.putParcelable(FRESQUE_BD_PLAY,fresqueBDPlay);
        MapGameFragment mapGameFragment = new MapGameFragment();
        mapGameFragment.setArguments(mapBundle);
        FragToolBox.loadFragment(this,R.id.fr_fresque_activity_frame,mapGameFragment);
    }

    @Override
    public void onClickResult() {
        fresqueSuivante();
    }

    private void fresqueSuivante(){
        fresqueNumber+=1;
        if (maxFresque > fresqueNumber) {
            fresqueBDPlay = parcoursBdChoisisArray.get(fresqueNumber);
            Bundle resultBd = new Bundle();
            resultBd.putParcelable(FRESQUE_BD_PLAY, fresqueBDPlay);
            PlayFragemnt playFragemnt = new PlayFragemnt();
            playFragemnt.setCallback(this);
            playFragemnt.setArguments(resultBd);
            FragToolBox.loadFragment(this, R.id.fr_fresque_activity_frame, playFragemnt);
        }else{
            Intent endGame = new Intent(this, EndGameActivity.class);
            startActivity(endGame);
        }
    }

    private void arriverALaFresque(){
        loca();
    }


    private Boolean checkPositionPlay(double latitude, double longitude){
        if(latitude-maPostionLatitude < 0.0001 && longitude - maPositonLongitude < 0.0001){
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
        double Fresquelatitude = fresqueBDPlay.getCoordonees().getLatitude();
        double Fresquelongitude = fresqueBDPlay.getCoordonees().getLongitude();
        maPostionLatitude = latitude;
        maPositonLongitude = longitude;
        Bundle resultBundle = new Bundle();
        if(checkPositionPlay(Fresquelatitude,Fresquelongitude)){
            resultBundle.putString(FRESQUE_RESULT,"bravo");
        }else{
            resultBundle.putString(FRESQUE_RESULT,"perdu");
        }
        ResultFragment resultFragment = new ResultFragment();
        resultFragment.setCallback(this);
        resultFragment.setArguments(resultBundle);
        FragToolBox.loadFragment(this,R.id.fr_fresque_activity_frame,resultFragment);
    }
}
