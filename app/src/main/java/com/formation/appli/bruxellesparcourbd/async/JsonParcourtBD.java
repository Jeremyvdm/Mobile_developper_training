package com.formation.appli.bruxellesparcourbd.async;

import android.os.AsyncTask;

import com.formation.appli.bruxellesparcourbd.async.tools.InputStreamOperations;
import com.formation.appli.bruxellesparcourbd.model.Coordonees;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.model.ParcourtBD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Jeremyvdm on 03/07/2017.
 */

public class



JsonParcourtBD extends AsyncTask <Integer, Void, ArrayList<FresqueBD>>{

    private ArrayList<FresqueBD> parcourtBDJson;

    //region Callback
    public interface JsonParcourtBDCallBack {
        void parcourt();
    }

    private JsonParcourtBDCallBack callback;
    public void setCallback(JsonParcourtBDCallBack callback) {
        this.callback = callback;
    }
    //endregion


    private static final String URLBASE_PARCOURT_BD = "http://opendata.bruxelles.be/api/v2/catalog/datasets/bruxelles_parcours_bd/records?";
    private static final String URL_PARCOURT_START = "start=";
    private static final String URL_PARCOURT_STOP = "stop=";

    @Override
    protected ArrayList<FresqueBD> doInBackground(Integer... integers) {
        parcourtBDJson = new ArrayList<>();
        String bxlParcourtBDURL = URLBASE_PARCOURT_BD + URL_PARCOURT_START + integers[0] + "&" + URL_PARCOURT_STOP + integers[1];

        JSONObject parcourtJson = resuqestJson(bxlParcourtBDURL);

        try {

            JSONArray parcourtJsonArray = parcourtJson.getJSONArray("records");
            int lenghtParcourtJson = parcourtJsonArray.length();
            for(int i =0; i < lenghtParcourtJson;i++){
                String fresquebdUrl = parcourtJsonArray.getJSONObject(i).getJSONArray("links").getJSONObject(0).getString("href");
                JSONObject jsonFresqueBD = resuqestJson(fresquebdUrl);
                FresqueBD fresqueBD = convertJsonToFresque(jsonFresqueBD);
                parcourtBDJson.add(fresqueBD);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parcourtBDJson;
    }

    private FresqueBD convertJsonToFresque(JSONObject jsonFresqueBD) {
        String titre = "";
        String auteur = "";
        int annee = 0;
        double longitude =0;
        double lattitude = 0;
        String ressourceIamge = "";
        Coordonees coordonée = null;
        try {
            JSONObject jsonField = jsonFresqueBD.getJSONObject("record").optJSONObject("fields");
            titre = jsonField.getString("personnage_s");
            auteur = jsonField.getString("auteur_s");
            annee = Integer.parseInt(jsonField.getString("annee"));
            JSONObject jsonCoordonees = jsonField.getJSONObject("coordonnees_geographiques");
            longitude = jsonCoordonees.getDouble("lon");
            lattitude = jsonCoordonees.getDouble("lat");
            JSONObject JsonPhoto = jsonField.getJSONObject("photo");
            ressourceIamge = JsonPhoto.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        coordonée = new Coordonees(longitude,lattitude);
        return new FresqueBD(titre,auteur,annee,coordonée,ressourceIamge);
    }

    public JSONObject resuqestJson(String urlString){
        JSONObject json=null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = connection.getInputStream();
            String result = InputStreamOperations.InputStreamToString(inputStream);

            json = new JSONObject(result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }

        return json;

    }

    public ParcourtBD getParcourt(){
        return new ParcourtBD(parcourtBDJson);
    }

}
