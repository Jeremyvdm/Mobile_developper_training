package com.formation.appli.bruxellesparcourbd.ui.parcourt;

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

import java.util.ArrayList;


public class ParcourtFresqueDetailFragment extends Fragment implements GetBitmapImageFromUrl.GetBitmapImageFromUrlCallBack {

    private Bundle extra;

    private int numeroParcourt;
    private ArrayList<FresqueBD> parcourFresqueBd;
    private String titreFresque;
    private String imageRessourceURL;

    private FresqueBD fresquebdchoisie;
    private TextView tv_fresque_titre;
    private TextView tv_fresque_auteur;
    private TextView tv_fresque_annee;
    private TextView tv_fresque_longitude;
    private TextView tv_fresque_latitude;

    private Bitmap imageBitmap;
    private String urlImage;

    private ImageView iv_fresque_image;

    private Button btn_fresque_detail_retour;

    public ParcourtFresqueDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requestImage();
        initParcourt();
        View v =  inflater.inflate(R.layout.fragment_parcourt_fresque_detail, container, false);
        v = initFragment(v);

        return v;
    }

    //region Singelton
    private static ParcourtFresqueDetailFragment instance;

    public static ParcourtFresqueDetailFragment getInstance(){
        if(instance == null){
            instance = new ParcourtFresqueDetailFragment();
        }
        return instance;
    }

    @Override
    public void getBitmap(Bitmap imageFresqueBitmap) {
        urlImage = fresquebdchoisie.getRessourceImage();
        iv_fresque_image.setImageBitmap(imageFresqueBitmap);
        displayInfo();
    }


    //endregion

    //region Communication
    public interface ParcourtFresqueDetailFragmentCallBack{
        void retour_liste();
    }

    private ParcourtFresqueDetailFragmentCallBack callback;
    public void setcallback(ParcourtActivity callback){
        this.callback =  callback;
    }
    //endregion

    private View initFragment(View v){

        tv_fresque_titre = (TextView) v.findViewById(R.id.tv_Parcourt_fresque_det_fresque_titre);
        tv_fresque_auteur = (TextView) v.findViewById(R.id.tv_Parcourt_fresque_det_fresque_auteur);
        tv_fresque_annee = (TextView) v.findViewById(R.id.tv_Parcourt_fresque_det_fresque_annee);
        tv_fresque_longitude = (TextView) v.findViewById(R.id.tv_Parcourt_fresque_det_fresque_longitude);
        tv_fresque_latitude = (TextView) v.findViewById(R.id.tv_Parcourt_fresque_det_fresque_latitude);
        iv_fresque_image = (ImageView) v.findViewById(R.id.iv_Parcourt_fresque_det_fresque_image);
        btn_fresque_detail_retour = (Button) v.findViewById(R.id.btn_Parcourt_fresque_det_retour);

        btn_fresque_detail_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCallBack();
            }
        });

        return v;

    }


    private void sendCallBack(){
        callback.retour_liste();
    }



    public void requestImage(){
        extra = this.getArguments();
        fresquebdchoisie = extra.getParcelable(ParcourtActivity.BD_CHOISIS);
        GetBitmapImageFromUrl getBitmapImageFromUrl = new GetBitmapImageFromUrl();
        getBitmapImageFromUrl.setCallback(this);
        getBitmapImageFromUrl.execute(urlImage);
    }


    private void displayInfo(){
        tv_fresque_titre.setText(fresquebdchoisie.getTitre());
        tv_fresque_auteur.setText(fresquebdchoisie.getAuteur());
        tv_fresque_annee.setText("" + fresquebdchoisie.getYears());
        double latitude = fresquebdchoisie.getCoordonees().getLatitude();
        double longitude = fresquebdchoisie.getCoordonees().getLongitude();
        tv_fresque_latitude.setText(Double.toString(latitude));
        tv_fresque_longitude.setText(Double.toString(longitude));
    }

    private void initParcourt(){


    }
}
