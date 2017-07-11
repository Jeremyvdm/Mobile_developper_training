package com.formation.appli.bruxellesparcourbd.ui.parcourt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.ui.FresqueBD.FresqueActivity;
import com.formation.appli.bruxellesparcourbd.ui.User.UserActivity;

public class ParcourtActivity extends AppCompatActivity implements ParcourtChoiceFragment.ParcourtChoiceFragmentCallBack, ParcourtListFresqueBDFragment.ParcourtListFresqueBDFragmentCallback, ParcourtFresqueDetailFragment.ParcourtFresqueDetailFragmentCallBack, ParcourtCarteFragment.ParcourtCarteFragmentFragmentCallback {

    private Bundle extra;
    private int numeroParcourtBDCHoisis;
    private ImageView iv_Parcourt_image;
    private Bundle bd;
    public static final String TITREFRESQUE = "Titre";

    public static final String PARCOURT_BD_CHOISIS = "parcourt_bd_choisi";
    public static final String BD_CHOISIS = "BD_choisie";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcourt);
        initfragment();
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
        extra = getIntent().getExtras();
        numeroParcourtBDCHoisis = extra.getInt(UserActivity.NUMERODEPARCOURT);
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
        fresqueIntent.putExtra(UserActivity.NUMERODEPARCOURT,numeroParcourtBDCHoisis);
        startActivity(fresqueIntent);
    }

    private void loadListe(){
        ParcourtListFresqueBDFragment listFresParcFrag = new ParcourtListFresqueBDFragment();
        bd.putInt(UserActivity.NUMERODEPARCOURT,numeroParcourtBDCHoisis);
        listFresParcFrag.setArguments(bd);
        listFresParcFrag.setCallback(this);
        loadFragment(R.id.fl_parcourt_frame, listFresParcFrag);
    }

    private void  loadMaps(){
        Bundle mapBundle = new Bundle();
        ParcourtCarteFragment mapParcFrag = new ParcourtCarteFragment();
        mapBundle.putInt(UserActivity.NUMERODEPARCOURT,numeroParcourtBDCHoisis);
        loadFragment(R.id.fl_parcourt_frame, mapParcFrag);
    }






    @Override
    public void onListClick(String titre, int numeroParcourtBDCHoisis) {
        Bundle fresqueBD = new Bundle();
        fresqueBD.putInt(UserActivity.NUMERODEPARCOURT,numeroParcourtBDCHoisis);
        fresqueBD.putString(TITREFRESQUE,titre);
        ParcourtFresqueDetailFragment detailFragment = new ParcourtFresqueDetailFragment();
        detailFragment.setArguments(fresqueBD);
        loadFragment(R.id.fl_parcourt_frame,detailFragment);
    }

    @Override
    public void retour_liste() {
        loadListe();
    }

    @Override
    public void retourMenu() {
        initfragment();
    }
}
