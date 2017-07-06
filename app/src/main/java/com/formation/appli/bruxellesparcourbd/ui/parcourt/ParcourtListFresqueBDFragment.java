package com.formation.appli.bruxellesparcourbd.ui.parcourt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.model.ParcourtBD;
import com.formation.appli.bruxellesparcourbd.ui.User.UserActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParcourtListFresqueBDFragment extends Fragment {

    private ParcourtBD parcourtBD;
    private FresqueBD fresqueBD;
    private int numéroParcourChoisi;
    private ListView lvFresqueBdList;
    ArrayList<String> titre;
    Bundle bdBundle;


    public ParcourtListFresqueBDFragment() {
        // Required empty public constructor
    }

    //region Singleton
    private static ParcourtListFresqueBDFragment instance;

    public static ParcourtListFresqueBDFragment getInstance() {
        if (instance == null) {
            instance = new ParcourtListFresqueBDFragment();
        }
        return instance;
    }
    //endregion

    //region Communication
    public interface ParcourtListFresqueBDFragmentCallback {
        void onListClick(String titre,FresqueBD bd);
    }

    private ParcourtListFresqueBDFragmentCallback callback;

    public void setCallback(ParcourtListFresqueBDFragmentCallback callback) {
        this.callback = callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_parcourt_list_fresque_bd, container, false);
        initParcourt();
        v = initFragment(v);

        return v;
    }

    private View initFragment(View v){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                titre
        );

        lvFresqueBdList = (ListView) v.findViewById(R.id.lv_parcourt_list_fresque_view);
        lvFresqueBdList.setAdapter(arrayAdapter);

        lvFresqueBdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String titre = (String) adapterView.getItemAtPosition(i);

                envoyerCallback(titre);
            }
        });

        return v;
    }

    private void envoyerCallback(String titre) {
        if (callback != null) {
            ArrayList<FresqueBD> parcourtBDList = parcourtBD.getParcourtFresqueBD();
            for(int i=0;i<parcourtBDList.size();i++){
                String titrefresqueI = parcourtBDList.get(i).getTitre();
                if(titre==titrefresqueI)
                    fresqueBD = parcourtBDList.get(i);
            }
            callback.onListClick(titre,fresqueBD);
        }
    }

    private void initParcourt(){
        titre = new ArrayList<>();
        bdBundle = this.getArguments();
        parcourtBD = bdBundle.getParcelable(ParcourtActivity.PARCOURT_BD_CHOISIS);
        numéroParcourChoisi = bdBundle.getInt(UserActivity.NUMERODEPARCOURT);
        ArrayList<FresqueBD> parcourtBDList = parcourtBD.getParcourtFresqueBD();
        for(int i=0; i<parcourtBDList.size(); i ++){
            FresqueBD fresqueBD = parcourtBDList.get(i);
            String titreFresque = fresqueBD.getTitre();
            titre.add(titreFresque);
        }
    }

}
