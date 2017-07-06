package com.formation.appli.bruxellesparcourbd.ui.FresqueBD;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.ui.parcourt.ParcourtActivity;

public class FresqueActivity extends AppCompatActivity {

    private Bundle extra;

    private String titre;

    private FresqueBD fresquebdchoisie;

    private TextView tv_fresque_titre;
    private TextView tv_fresque_auteur;
    private TextView tv_fresque_annee;
    private TextView tv_fresque_longitude;
    private TextView tv_fresque_latitude;

    private ImageView iv_fresque_num_parcour;
    private ImageView iv_fresque_image;

    private Button btn_fresque_button_1;
    private Button btn_fresque_button_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresque);
        initView();
    }



    private void initView(){
        extra = getIntent().getExtras();
        fresquebdchoisie = extra.getParcelable(ParcourtActivity.BD_CHOISIS);
        tv_fresque_titre = (TextView) findViewById(R.id.tv_fresque_act_fresque_titre);
        tv_fresque_auteur = (TextView) findViewById(R.id.tv_fresque_act_fresque_auteur);
        tv_fresque_annee = (TextView) findViewById(R.id.tv_fresque_act_fresque_annee);
        tv_fresque_longitude = (TextView) findViewById(R.id.tv_fresque_act_fresque_longitude);
        tv_fresque_latitude = (TextView) findViewById(R.id.tv_fresque_act_fresque_latitude);
        iv_fresque_num_parcour = (ImageView) findViewById(R.id.iv_fresque_act_parc_nb);
        iv_fresque_image = (ImageView) findViewById(R.id.iv_fresque_act_fresque_image);
        btn_fresque_button_1 = (Button) findViewById(R.id.btn_fresque_act_btn1);
        btn_fresque_button_2 = (Button) findViewById(R.id.btn_fresque_act_btn2);

        tv_fresque_titre.setText(fresquebdchoisie.getTitre());
        tv_fresque_auteur.setText(fresquebdchoisie.getAuteur());
        tv_fresque_annee.setText(fresquebdchoisie.getYears());
        double latitude = fresquebdchoisie.getCoordonees().getLatitude();
        double longitude = fresquebdchoisie.getCoordonees().getLongitude();
        tv_fresque_latitude.setText(Double.toString(latitude));
        tv_fresque_longitude.setText(Double.toString(longitude));

    }
}
