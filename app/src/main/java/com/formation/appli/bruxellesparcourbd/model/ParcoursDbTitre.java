package com.formation.appli.bruxellesparcourbd.model;

import com.formation.appli.bruxellesparcourbd.DB.ParcoursBDDAO;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeremyvdm on 13/07/2017.
 */

public class ParcoursDbTitre {
    private String parcoursBdId;
    private String parcoursTitre;
    private ArrayList<String> parcoursTitreFresque;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public ParcoursDbTitre(){
        //constructeur vide nécéssaire pour appeler la méthode DataSnapshot.getValue(ParcoursDdbBdTitre.class)
    }

    public ParcoursDbTitre(String parcoursBdId, String parcoursTitre, ArrayList<String> parcourtTitreFresque) {
        this.parcoursBdId = parcoursBdId;
        this.parcoursTitreFresque = parcourtTitreFresque;
        this.parcoursTitre = parcoursTitre;
    }

    public ParcoursDbTitre( String parcoursTitre, ArrayList<String> parcourtTitreFresque) {
        this.parcoursBdId = null;
        this.parcoursTitreFresque = parcourtTitreFresque;
        this.parcoursTitre = parcoursTitre;
    }

    public String getParcoursBdId() {
        return parcoursBdId;
    }

    public String getParcoursTitre() {
        return parcoursTitre;
    }

    public void setParcoursTitre(String parcoursTitre) {
        this.parcoursTitre = parcoursTitre;
    }

    public void setParcoursBdId(String parcoursBdId) {
        this.parcoursBdId = parcoursBdId;
    }

    public ArrayList<String> getParcoursTitreFresque() {
        return parcoursTitreFresque;
    }

    public void setParcoursTitreFresque(ArrayList<String> parcoursTitreFresque) {
        this.parcoursTitreFresque = parcoursTitreFresque;
    }

    //region database
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put(ParcoursBDDAO.COLUMN_PARCOURSBD_ID, parcoursBdId);
        result.put(ParcoursBDDAO.COLUMN_PARCOURSBD_TITRE, parcoursTitre);
        result.put(ParcoursBDDAO.COLUMN_PARCOURSBD_TITRE_FRESQUE, parcoursTitreFresque);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
    //endregion

}
