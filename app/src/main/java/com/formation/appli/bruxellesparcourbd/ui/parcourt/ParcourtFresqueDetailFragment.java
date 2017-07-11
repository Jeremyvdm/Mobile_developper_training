package com.formation.appli.bruxellesparcourbd.ui.parcourt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.formation.appli.bruxellesparcourbd.Asynch.JsonParcourtBD;
import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.ui.User.UserActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class ParcourtFresqueDetailFragment extends Fragment implements JsonParcourtBD.JsonParcourtBDCallBack{

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

    private void initParcourt(){
        extra = this.getArguments();
        numeroParcourt = extra.getInt(UserActivity.NUMERODEPARCOURT);
        titreFresque = extra.getString(ParcourtActivity.TITREFRESQUE);
        JsonParcourtBD parcourJson= new JsonParcourtBD();
        parcourJson.setCallback(this);
        parcourJson.execute(numeroParcourt);

    }


    private void sendCallBack(){
        callback.retour_liste();
    }

    public void setImage(){
        urlImage = fresquebdchoisie.getRessourceImage();
        imageBitmap = requestImage(urlImage);
        iv_fresque_image.setImageBitmap(imageBitmap);
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

    public Bitmap requestImage(String urlString){
        Bitmap imageurl=null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();

            inputStream = connection.getInputStream();

            imageurl = BitmapFactory.decodeStream(inputStream, null, bmOptions);
            inputStream.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }

        return imageurl;

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

    @Override
    public void parcourt(ArrayList<FresqueBD> parcourBDJson) {
        Toast.makeText(this.getActivity(),"le parcourt a été correctemenet chargé", Toast.LENGTH_SHORT).show();
        parcourFresqueBd = parcourBDJson;
        fresquebdchoisie = getFresqueBD(parcourFresqueBd,titreFresque);
        setImage();
        displayInfo();
    }

    public Bitmap getImageBitmap(){
        return imageBitmap;
    }

}
