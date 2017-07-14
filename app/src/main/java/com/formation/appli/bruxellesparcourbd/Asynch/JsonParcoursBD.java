package com.formation.appli.bruxellesparcourbd.Asynch;

import android.os.AsyncTask;
import android.util.Log;

import com.formation.appli.bruxellesparcourbd.model.Coordonees;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.tools.InputStreamOperations;

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

//récupere le parcourt avec le Json de l'opendata prévu par la ville de Bruxelles qui reprend toutes les fresques de bd présentent dans la ville de Bruxelles
public class JsonParcoursBD extends AsyncTask <Integer, Void, ArrayList<FresqueBD>>{
// déclaration des variables
    private ArrayList<FresqueBD> parcourtBDJson;
    private int debut;
    private int fin;

    //region Callback
    public interface JsonParcourtBDCallBack {
        void parcourt( ArrayList<FresqueBD> parcourtFresqueJson);
    }

    private JsonParcourtBDCallBack callback;
    public void setCallback(JsonParcourtBDCallBack callback) {
        this.callback = callback;
    }
    //endregion

// déclaration de l'url en partie sous forme de constante
    private static final String URLBASE_PARCOURT_BD = "http://opendata.bruxelles.be/api/v2/catalog/datasets/bruxelles_parcours_bd/records?";
    private static final String URL_PARCOURT_START = "start=";
    private static final String URL_PARCOURT_STOP = "stop=";
//initalisation du callback
    @Override
    protected void onPostExecute(ArrayList<FresqueBD> fresqueBDs) {
        if(callback!=null){
            callback.parcourt(parcourtBDJson);
        }
    }
//récupération du parcourt sous forme de ArrayList<FresqueBD>
    @Override
    protected ArrayList<FresqueBD> doInBackground(Integer... integers) {
        parcourtBDJson = new ArrayList<>();
        int idParcourt = integers[0];
        initParcourt(idParcourt);
        String bxlParcourtBDURL = URLBASE_PARCOURT_BD + URL_PARCOURT_START + debut + "&" + URL_PARCOURT_STOP + fin;
        Log.v("TEST_URI_API",bxlParcourtBDURL);
        JSONObject parcourtJson = resuqestJson(bxlParcourtBDURL);
        JSONArray parcourtJsonArray = jsonArrayParcourt(parcourtJson);
        parcourtBDJson = convertJsorrayToFresqueBD(parcourtJsonArray);

        return parcourtBDJson;
    }
    // Transformation du Json en fresque bd
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
// transformation du json objet en json array
    public JSONArray jsonArrayParcourt(JSONObject jsonparcourt){
        JSONArray parcourtJsonArray = new JSONArray();
        try {

            parcourtJsonArray = jsonparcourt.getJSONArray("records");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parcourtJsonArray;
    }
    // Transformation du JsonArray en ArrayList<fresqueBD>
    public ArrayList<FresqueBD> convertJsorrayToFresqueBD(JSONArray jsonArray){
        int lenghtParcourtJson = jsonArray.length();
        ArrayList<FresqueBD> FresqueParcourtFromArray = new ArrayList<>();
        for(int i =0; i < lenghtParcourtJson;i++) {
            String fresquebdUrl = null;
            try {
                fresquebdUrl = jsonArray.getJSONObject(i).getJSONArray("links").getJSONObject(0).getString("href");
                JSONObject jsonFresqueBD = resuqestJson(fresquebdUrl);
                FresqueBD fresqueBD = convertJsonToFresque(jsonFresqueBD);
                FresqueParcourtFromArray.add(fresqueBD);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

            return FresqueParcourtFromArray;
    }
    //fonction qui va transformer un url en Json objet
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



// Fonction qui va initialiser les bornes du début et de fin du parcours
    public void initParcourt(int idParcourt){
        switch (idParcourt){
            case 1:
                debut = 0;
                fin = 10;
                break;
            case 2:
                debut = 10;
                fin = 20;
                break;
            case 3:
                debut = 20;
                fin = 30;
                break;
            case 4:
                debut = 30;
                fin = 40;
                break;
            case 5:
                debut = 40;
                fin = 52;
                break;
            case 6:
                debut = 0;
                fin = 52;
                break;
        }
    }

}
