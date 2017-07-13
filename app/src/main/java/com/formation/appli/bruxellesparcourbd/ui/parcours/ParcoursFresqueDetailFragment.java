package com.formation.appli.bruxellesparcourbd.ui.parcours;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
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


public class ParcoursFresqueDetailFragment extends Fragment implements GetBitmapImageFromUrl.GetBitmapImageFromUrlCallBack {

    private Bundle extra;

    private FresqueBD fresquebdchoisie;

    private String urlRessourcesImage;

    private View v;

    private TextView tv_fresque_titre;
    private TextView tv_fresque_auteur;
    private TextView tv_fresque_annee;
    private TextView tv_fresque_longitude;
    private TextView tv_fresque_latitude;

    private ImageView iv_fresque_image;

    private Button btn_fresque_detail_retour;

    public ParcoursFresqueDetailFragment() {
        // Required empty public constructor
    }





    //region Singelton
    private static ParcoursFresqueDetailFragment instance;

    public static ParcoursFresqueDetailFragment getInstance(){
        if(instance == null){
            instance = new ParcoursFresqueDetailFragment();
        }
        return instance;
    }




    //endregion

    //region Communication
    public interface ParcourtFresqueDetailFragmentCallBack{
        void imageChargee();
    }

    private ParcourtFresqueDetailFragmentCallBack callback;
    public void setcallback(ParcoursActivity callback){
        this.callback =  callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initParcourt();
        requestImage();
        v =  inflater.inflate(R.layout.fragment_parcours_fresque_detail, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        v = initFragment(v);
    }

    private View initFragment(View v){
        tv_fresque_titre = (TextView) v.findViewById(R.id.tv_Parcours_fresque_det_fresque_titre);
        tv_fresque_auteur = (TextView) v.findViewById(R.id.tv_Parcours_fresque_det_fresque_auteur);
        tv_fresque_annee = (TextView) v.findViewById(R.id.tv_Parcours_fresque_det_fresque_annee);
        tv_fresque_longitude = (TextView) v.findViewById(R.id.tv_Parcours_fresque_det_fresque_longitude);
        tv_fresque_latitude = (TextView) v.findViewById(R.id.tv_Parcours_fresque_det_fresque_latitude);
        iv_fresque_image = (ImageView) v.findViewById(R.id.iv_Parcours_fresque_det_fresque_image);
        return v;

    }


    private void sendCallBack(){
        if(callback!=null) {
            callback.imageChargee();
        }
    }



    public void requestImage(){
        GetBitmapImageFromUrl getBitmapImageFromUrl = new GetBitmapImageFromUrl();
        getBitmapImageFromUrl.setCallback(this);
        getBitmapImageFromUrl.execute(urlRessourcesImage);
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
        extra = this.getArguments();
        fresquebdchoisie = extra.getParcelable(ParcoursActivity.BD_CHOISIS);
        urlRessourcesImage = fresquebdchoisie.getRessourceImage();
    }

    @Override
    public void getBitmap(Bitmap imageFresqueBitmap) {
        displayInfo();

        iv_fresque_image.setImageBitmap(imageFresqueBitmap);
        iv_fresque_image.setAdjustViewBounds(true);
        sendCallBack();
    }
}
