package com.formation.appli.bruxellesparcourbd.ui.parcourt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.formation.appli.bruxellesparcourbd.Asynch.JsonParcourtBD;
import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.model.ParcourtBD;
import com.formation.appli.bruxellesparcourbd.ui.Game.GameActivity;
import com.formation.appli.bruxellesparcourbd.ui.User.UserActivity;

import java.util.ArrayList;

public class ParcourtActivity extends AppCompatActivity implements JsonParcourtBD.JsonParcourtBDCallBack, ParcourtChoiceFragment.ParcourtChoiceFragmentCallBack, ParcourtListFresqueBDFragment.ParcourtListFresqueBDFragmentCallback, ParcourtFresqueDetailFragment.ParcourtFresqueDetailFragmentCallBack, ParcourtCarteFragment.ParcourtCarteFragmentFragmentCallback {

    private Bundle extra;
    private int numeroParcourtBDCHoisis;
    private ImageView iv_Parcourt_image;
    public static final String TITREFRESQUE = "Titre";
    private ParcourtBD parcourtBdComplet;
    private JsonParcourtBD parcourJson;

    private ParcourtListFresqueBDFragment listFresParcFrag;
    private ParcourtCarteFragment mapParcFrag;
    private ParcourtChoiceFragment parcourtChoiceFragment;
    private ParcourtFresqueDetailFragment detailFresqueFragment;

    public static final String PARCOURT_BD_CHOISIS = "parcourt_bd_choisi";
    public static final String BD_CHOISIS = "BD_choisie";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcourt);
        initParcourt();
        initfragment();
    }

    private void initParcourt(){
        extra = this.getIntent().getExtras();
        numeroParcourtBDCHoisis = extra.getInt(UserActivity.NUMERODEPARCOURT);
        parcourJson= new JsonParcourtBD();
        parcourJson.setCallback(this);
        parcourJson.execute(numeroParcourtBDCHoisis);
    }

    @Override
    public void parcourt(ArrayList<FresqueBD> parcourBDJson) {
        Toast.makeText(this, "le parcourt a été correctemenet chargé", Toast.LENGTH_SHORT).show();
        parcourtBdComplet = new ParcourtBD(parcourBDJson);
    }

    private void initfragment(){
        initView();
        parcourtChoiceFragment = ParcourtChoiceFragment.getInstance();
        parcourtChoiceFragment.setcallback(this);
        loadFragment(R.id.fl_parcourt_frame, parcourtChoiceFragment);
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
        Intent fresqueIntent  = new Intent(this,GameActivity.class);
        fresqueIntent.putExtra(UserActivity.NUMERODEPARCOURT,numeroParcourtBDCHoisis);
        fresqueIntent.putExtra(PARCOURT_BD_CHOISIS,parcourtBdComplet);
        startActivity(fresqueIntent);
    }

    private void loadListe(){
        Bundle bd = new Bundle();
        listFresParcFrag = new ParcourtListFresqueBDFragment();
        bd.putInt(UserActivity.NUMERODEPARCOURT,numeroParcourtBDCHoisis);
        bd.putParcelable(PARCOURT_BD_CHOISIS,parcourtBdComplet);
        listFresParcFrag.setArguments(bd);
        listFresParcFrag.setCallback(this);
        loadFragment(R.id.fl_parcourt_frame, listFresParcFrag);
    }

    private void  loadMaps(){
        Bundle mapBundle = new Bundle();
        mapParcFrag = new ParcourtCarteFragment();
        mapBundle.putInt(UserActivity.NUMERODEPARCOURT,numeroParcourtBDCHoisis);
        mapBundle.putParcelable(PARCOURT_BD_CHOISIS,parcourtBdComplet);
        mapParcFrag.setArguments(mapBundle);
        mapParcFrag.setCallback(this);
        loadFragment(R.id.fl_parcourt_frame, mapParcFrag);
    }




    private FresqueBD getFresqueBD(ArrayList<FresqueBD> parcourt, String titre){
        FresqueBD fresqueBD = null;
        for(int i=0;i<parcourt.size();i++){
            String titreFresqueI = parcourt.get(i).getTitre();
            if(titreFresqueI.equals(titre)){
                fresqueBD = parcourt.get(i);
                break;
            }
        }
        return fresqueBD;

    }

    @Override
    public void getDetailFresque(String titre) {
        ArrayList<FresqueBD> parcourtFresqueBD = parcourtBdComplet.getParcourtFresqueBD();
        FresqueBD fresquebdchoisie = getFresqueBD(parcourtFresqueBD,titre);
        Bundle fresqueBD = new Bundle();
        fresqueBD.putString(TITREFRESQUE,titre);
        fresqueBD.putParcelable(BD_CHOISIS, fresquebdchoisie);
        detailFresqueFragment = new ParcourtFresqueDetailFragment();
        detailFresqueFragment.setArguments(fresqueBD);
        detailFresqueFragment.setcallback(this);
        loadFragment(R.id.fl_parcourt_frame,detailFresqueFragment);
    }

    @Override
    public void retour_liste() {
        this.getSupportFragmentManager().beginTransaction().remove(detailFresqueFragment).commit();
    }
}
