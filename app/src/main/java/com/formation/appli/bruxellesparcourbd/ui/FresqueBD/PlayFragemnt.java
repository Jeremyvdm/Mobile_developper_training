package com.formation.appli.bruxellesparcourbd.ui.FresqueBD;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.formation.appli.bruxellesparcourbd.Asynch.GetBitmapImageFromUrl;
import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;


public class PlayFragemnt extends Fragment implements GetBitmapImageFromUrl.GetBitmapImageFromUrlCallBack, View.OnClickListener{

    private Bundle extra;
    private FresqueBD fresquebdPlay;

    private String urlRessourcesImage;

    private TextView tv_fresque_titre;
    private TextView tv_fresque_auteur;
    private TextView tv_fresque_annee;
    private TextView tv_fresque_longitude;
    private TextView tv_fresque_latitude;

    private ImageView iv_fresque_image;

    private Button btn_play_fresque_fresque_suivante;
    private Button btn_play_fresque_arriver;

    public PlayFragemnt() {
        // Required empty public constructor
    }

    //region Singleton
    private static PlayFragemnt instance;

    public static PlayFragemnt getInstance() {
        if (instance == null) {
            instance = new PlayFragemnt();
        }
        return instance;
    }


    //endregion

    //region Communication
    public interface playFragemntCallback {
        void onClickPlay(int id);
    }

    private playFragemntCallback callback;

    public void setCallback(playFragemntCallback callback) {
        this.callback = callback;
    }
    //endregion



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_play_fragemnt, container, false);
        initParcourt();
        v = initFragment(v);

        return v;
    }


    private View initFragment(View v){
        tv_fresque_titre = (TextView) v.findViewById(R.id.tv_Play_fresque_det_fresque_titre);
        tv_fresque_auteur = (TextView) v.findViewById(R.id.tv_Play_fresque_det_fresque_auteur);
        tv_fresque_annee = (TextView) v.findViewById(R.id.tv_Play_fresque_det_fresque_annee);
        tv_fresque_longitude = (TextView) v.findViewById(R.id.tv_Play_fresque_det_fresque_longitude);
        tv_fresque_latitude = (TextView) v.findViewById(R.id.tv_Play_fresque_det_fresque_latitude);
        iv_fresque_image = (ImageView) v.findViewById(R.id.iv_Play_fresque_det_fresque_image);
        btn_play_fresque_fresque_suivante = (Button) v.findViewById(R.id.btn_Play_fresque_det_next_fresque);
        btn_play_fresque_fresque_suivante = (Button) v.findViewById(R.id.btn_Play_fresque_det_arriver);

        btn_play_fresque_arriver.setOnClickListener(this);
        btn_play_fresque_fresque_suivante.setOnClickListener(this);

        return v;
    }

    private void initParcourt(){
        extra = this.getArguments();
        fresquebdPlay = extra.getParcelable(FresqueActivity.FRESQUE_BD_PLAY);
        urlRessourcesImage = fresquebdPlay.getRessourceImage();
    }

    private void displayInfo(FresqueBD fresqueBD){
        tv_fresque_titre.setText(fresqueBD.getTitre());
        tv_fresque_auteur.setText(fresqueBD.getAuteur());
        tv_fresque_annee.setText("" + fresqueBD.getYears());
        double latitude = fresqueBD.getCoordonees().getLatitude();
        double longitude = fresqueBD.getCoordonees().getLongitude();
        tv_fresque_latitude.setText(Double.toString(latitude));
        tv_fresque_longitude.setText(Double.toString(longitude));
    }


    @Override
    public void getBitmap(Bitmap imageFresqueBitmap) {
        iv_fresque_image.setImageBitmap(imageFresqueBitmap);
        displayInfo(fresquebdPlay);
    }

    @Override
    public void onClick(View view) {
        sendCallBack(view.getId());
    }

    private void sendCallBack(int id){
        callback.onClickPlay(id);
    }



}
