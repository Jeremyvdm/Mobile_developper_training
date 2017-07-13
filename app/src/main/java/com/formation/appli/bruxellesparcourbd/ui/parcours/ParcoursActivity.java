package com.formation.appli.bruxellesparcourbd.ui.parcours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.formation.appli.bruxellesparcourbd.Asynch.JsonParcoursBD;
import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.model.ParcoursBD;
import com.formation.appli.bruxellesparcourbd.model.ParcoursDbTitre;
import com.formation.appli.bruxellesparcourbd.tools.FragToolBox;
import com.formation.appli.bruxellesparcourbd.ui.Game.GameActivity;
import com.formation.appli.bruxellesparcourbd.ui.User.UserActivity;

import java.util.ArrayList;

public class ParcoursActivity extends AppCompatActivity implements JsonParcoursBD.JsonParcourtBDCallBack, ParcoursChoiceFragment.ParcoursChoiceFragmentCallBack, ParcoursListFresqueBDFragment.ParcoursListFresqueBDFragmentCallback, ParcoursFresqueDetailFragment.ParcourtFresqueDetailFragmentCallBack, ParcoursCarteFragment.ParcoursCarteFragmentFragmentCallback {

    private Bundle extra;
    private int numeroParcoursBDCHoisis;
    private ImageView iv_Parcours_image;
    public static final String TITREFRESQUE = "Titre";
    private ParcoursBD parcoursBdComplet;
    private JsonParcoursBD parcourJson;

    String parcoursName;

    private ParcoursListFresqueBDFragment listFresParcFrag;
    private ParcoursCarteFragment mapParcFrag;
    private ParcoursChoiceFragment parcoursChoiceFragment;
    private ParcoursFresqueDetailFragment detailFresqueFragment;

    public static final String PARCOURS_BD_CHOISIS = "parcourt_bd_choisi";
    public static final String BD_CHOISIS = "BD_choisie";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcours);
        initParcourt();

    }

    private void initParcourt(){
        extra = this.getIntent().getExtras();
        numeroParcoursBDCHoisis = extra.getInt(UserActivity.NUMERODEPARCOURS);
        parcourJson= new JsonParcoursBD();
        parcourJson.setCallback(this);
        parcourJson.execute(numeroParcoursBDCHoisis);
    }

    @Override
    public void parcourt(ArrayList<FresqueBD> parcourBDJson) {
        Toast.makeText(this, R.string.toast_parcours_charger, Toast.LENGTH_SHORT).show();
        parcoursBdComplet = new ParcoursBD(parcourBDJson);

        initView();
        initfragment();
    }

    private void initfragment(){
        parcoursChoiceFragment = ParcoursChoiceFragment.getInstance();
        parcoursChoiceFragment.setcallback(this);
        FragToolBox.loadFragment(this,R.id.fl_parcours_frame, parcoursChoiceFragment);
    }


    private void initView(){
        extra = getIntent().getExtras();
        numeroParcoursBDCHoisis = extra.getInt(UserActivity.NUMERODEPARCOURS);
        iv_Parcours_image = (ImageView) findViewById(R.id.iv_parcours_image);
        switch (numeroParcoursBDCHoisis){
            case 1:
                iv_Parcours_image.setImageResource(R.drawable.parcour_n_1);
                parcoursName = "parcours_Rouge";
                break;
            case 2:
                iv_Parcours_image.setImageResource(R.drawable.parcour_n_2);
                parcoursName = "parcours_Jaune";
                break;
            case 3:
                iv_Parcours_image.setImageResource(R.drawable.parcour_n_3);
                parcoursName = "parcours_Bleu";
                break;
            case 4:
                iv_Parcours_image.setImageResource(R.drawable.parcour_n_4);
                parcoursName = "parcours_Vert";
                break;
            case 5:
                iv_Parcours_image.setImageResource(R.drawable.parcour_n_5);
                parcoursName = "parcours_Orange";
                break;
            default:
                iv_Parcours_image.setImageResource(R.drawable.parcout_bd_global);
                break;
        }
    }

    @Override
    public void onClick(int id) {
        switch (id){
            case R.id.btn_parcours_carte:
                loadMaps();
                break;
            case R.id.btn_parcours_liste_fresque:
                loadListe();
                break;
            case R.id.btn_parcours_play:
                play();
                break;
        }
    }

    private void play(){
        ArrayList<String> parcourBdTitre = new ArrayList<>();
        for(FresqueBD fresqueBD : parcoursBdComplet.getParcoursFresqueBD()){
            String titreFresque = fresqueBD.getTitre();
            parcourBdTitre.add(titreFresque);
        }
        ParcoursDbTitre parcoursDbTitre = new ParcoursDbTitre(parcoursName,parcourBdTitre);
        Intent fresqueIntent  = new Intent(this,GameActivity.class);
        fresqueIntent.putExtra(UserActivity.NUMERODEPARCOURS, numeroParcoursBDCHoisis);
        fresqueIntent.putExtra(PARCOURS_BD_CHOISIS, parcoursBdComplet);
        startActivity(fresqueIntent);
    }

    private void loadListe(){
        Bundle bd = new Bundle();
        listFresParcFrag = new ParcoursListFresqueBDFragment();
        bd.putInt(UserActivity.NUMERODEPARCOURS, numeroParcoursBDCHoisis);
        bd.putParcelable(PARCOURS_BD_CHOISIS, parcoursBdComplet);
        listFresParcFrag.setArguments(bd);
        listFresParcFrag.setCallback(this);
        FragToolBox.loadFragment(this,R.id.fl_parcours_frame, listFresParcFrag);
    }

    private void  loadMaps(){
        Bundle mapBundle = new Bundle();
        mapParcFrag = new ParcoursCarteFragment();
        mapBundle.putInt(UserActivity.NUMERODEPARCOURS, numeroParcoursBDCHoisis);
        mapBundle.putParcelable(PARCOURS_BD_CHOISIS, parcoursBdComplet);
        mapParcFrag.setArguments(mapBundle);
        mapParcFrag.setCallback(this);
        FragToolBox.loadFragment(this,R.id.fl_parcours_frame, mapParcFrag);
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
        ArrayList<FresqueBD> parcourtFresqueBD = parcoursBdComplet.getParcoursFresqueBD();
        FresqueBD fresquebdchoisie = getFresqueBD(parcourtFresqueBD,titre);
        Bundle fresqueBD = new Bundle();
        fresqueBD.putString(TITREFRESQUE,titre);
        fresqueBD.putParcelable(BD_CHOISIS, fresquebdchoisie);
        detailFresqueFragment = new ParcoursFresqueDetailFragment();
        detailFresqueFragment.setArguments(fresqueBD);
        detailFresqueFragment.setcallback(this);
        FragToolBox.loadFragment(this,R.id.fl_parcours_frame,detailFresqueFragment);
    }

    @Override
    public void imageChargee() {
        Toast.makeText(this,R.string.toast_image_chargement,Toast.LENGTH_LONG);
    }
}
