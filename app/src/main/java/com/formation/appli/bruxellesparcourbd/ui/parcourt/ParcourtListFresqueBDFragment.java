package com.formation.appli.bruxellesparcourbd.ui.parcourt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.formation.appli.bruxellesparcourbd.Asynch.JsonParcourtBD;
import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParcourtListFresqueBDFragment extends Fragment {


    private int numeroParcourChoisi;
    private ListView lvFresqueBdList;
    JsonParcourtBD parcourJson;
    ArrayList<String> titre;
    ArrayList<FresqueBD> parcourFresqueBd;
    Bundle bdBundle;

    ArrayAdapter<String> arrayAdapter;


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
        void onListClick(String titre,int numéroParcourt);
    }

    private ParcourtListFresqueBDFragmentCallback callback;

    public void setCallback(ParcourtListFresqueBDFragmentCallback callback) {
        this.callback = callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initvariable();
        initParcourt();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_parcourt_list_fresque_bd, container, false);
        v = initFragment(v);

        return v;
    }

    private void initvariable(){
        parcourFresqueBd = new ArrayList<>();
        titre = new ArrayList<>();
        bdBundle = this.getArguments();
    }

    private View initFragment(View v){

        lvFresqueBdList = (ListView) v.findViewById(R.id.lv_parcourt_list_fresque_view);
        initParcourt();
        return v;
    }


    private ArrayList<String> listeTitreFresque(){
            ArrayList<String> listFresqueBd = new ArrayList<>();
            for (int i=0; i<parcourFresqueBd.size();i++){
                FresqueBD bd = parcourFresqueBd.get(i);
                String titre = bd.getTitre();
                listFresqueBd.add(titre);
            }
            return listFresqueBd;
    }

    private void envoyerCallback(String titre) {
        if (callback != null) {
            callback.onListClick(titre, numeroParcourChoisi);
        }
    }



    private void initListFresque(){
        arrayAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                titre
        );
        lvFresqueBdList.setAdapter(arrayAdapter);

        lvFresqueBdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String titre = (String) adapterView.getItemAtPosition(i);
                envoyerCallback(titre);
            }
        });
    }

        private void initParcourt(){
        parcourFresqueBd = bdBundle.getParcelable(ParcourtActivity.PARCOURT_BD_CHOISIS);
        titre = listeTitreFresque();
        initListFresque();
    }

}
