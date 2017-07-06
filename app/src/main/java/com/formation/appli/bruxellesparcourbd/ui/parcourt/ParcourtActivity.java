package com.formation.appli.bruxellesparcourbd.ui.parcourt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.async.JsonParcourtBD;
import com.formation.appli.bruxellesparcourbd.model.ParcourtBD;
import com.formation.appli.bruxellesparcourbd.ui.FresqueBD.FresqueActivity;
import com.formation.appli.bruxellesparcourbd.ui.User.UserActivity;

public class ParcourtActivity extends AppCompatActivity implements ParcourtChoiceFragment.ParcourtChoiceFragmentCallBack,JsonParcourtBD.JsonParcourtBDCallBack, ParcourtListFresqueBDFragment.ParcourtListFresqueBDFragmentCallback{

    private Bundle extra;
    private ParcourtBD parcourtBdChoisi;
    private int numeroParcourtBDCHoisis;
    private int debut;
    private int fin;
    private ImageView iv_Parcourt_image;
    private Bundle bd;
    private static final String TITRE = "Titre";


    public static final String PARCOURT_BD_CHOISIS = "parcourt_choisis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcourt);
        initParcourt();
        initfragment();
    }

    private void initParcourt() {
        extra = getIntent().getExtras();
        numeroParcourtBDCHoisis = extra.getInt(UserActivity.NUMERODEPARCOURT);
        debut = extra.getInt(UserActivity.PARCOURTDEBUT);
        fin = extra.getInt(UserActivity.PARCOURTFIN);
        JsonParcourtBD parcourt =  new JsonParcourtBD();
        parcourt.setCallback(this);
        parcourt.execute(debut,fin);
        parcourtBdChoisi =  parcourt.getParcourt();
    }


    private void initfragment(){
        initView();
        ParcourtChoiceFragment fragment = ParcourtChoiceFragment.getInstance();
        fragment.setcallback(this);
        loadFragment(R.id.fl_parcourt_frame, fragment);
    }

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

    private void initView(){
        iv_Parcourt_image = (ImageView) findViewById(R.id.iv_parcourt_image);
        switch (numeroParcourtBDCHoisis){
            case 1:
                iv_Parcourt_image.setImageResource(R.drawable.parcour_n_1);
                break;
            case 2:
                iv_Parcourt_image.setImageResource(R.drawable.parcour_n_2);
                break;
            case 3:
                iv_Parcourt_image.setImageResource(R.drawable.parcour_n_3);
                break;
            case 4:
                iv_Parcourt_image.setImageResource(R.drawable.parcour_n_4);
                break;
            case 5:
                iv_Parcourt_image.setImageResource(R.drawable.parcour_n_5);
                break;
            default:
                iv_Parcourt_image.setImageResource(R.drawable.parcout_bd_global);
                break;
        }
        bd = new Bundle();
    }

    @Override
    public void onClick(int id) {
        switch (id){
            case R.id.btn_parcourt_carte:
                loadMaps();
                break;
            case R.id.btn_parcourt_liste_fresque:
                loadListe();
                break;
            case R.id.btn_parcourt_play:
                play();
                break;
        }
    }

    private void play(){
        Intent fresqueIntent  = new Intent(this,FresqueActivity.class);
        fresqueIntent.putExtra(PARCOURT_BD_CHOISIS,parcourtBdChoisi);
        fresqueIntent.putExtra(UserActivity.NUMERODEPARCOURT,numeroParcourtBDCHoisis);
        startActivity(fresqueIntent);
    }

    private void loadListe(){
        ParcourtListFresqueBDFragment listFresParcFrag = new ParcourtListFresqueBDFragment();
        bd.putInt(UserActivity.NUMERODEPARCOURT,numeroParcourtBDCHoisis);
        bd.putParcelable(PARCOURT_BD_CHOISIS,parcourtBdChoisi);
        listFresParcFrag.setArguments(bd);
        listFresParcFrag.setCallback(this);
        loadFragment(R.id.fl_parcourt_frame, listFresParcFrag);
    }

    private void  loadMaps(){
        ParcourtCarteFragment mapParcFrag = new ParcourtCarteFragment();
        loadFragment(R.id.fl_parcourt_frame, mapParcFrag);
    }

    @Override
    public void parcourt() {
        Toast.makeText(this,getString(R.string.toast_parcourt_choisis),Toast.LENGTH_LONG);
    }

    @Override
    public void onListClick(String titre) {
        Intent fresqueBDIntent = new Intent(this, FresqueActivity.class);
        fresqueBDIntent.putExtra(TITRE,titre);
        fresqueBDIntent.putExtra(PARCOURT_BD_CHOISIS,parcourtBdChoisi);
        startActivity(fresqueBDIntent);
    }
}
