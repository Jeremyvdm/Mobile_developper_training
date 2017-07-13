package com.formation.appli.bruxellesparcourbd.ui.parcours;


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
import com.formation.appli.bruxellesparcourbd.model.ParcoursBD;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParcoursListFresqueBDFragment extends Fragment {


    private int numeroParcourChoisi;
    private ListView lvFresqueBdList;
    ArrayList<String> titre;
    ArrayList<FresqueBD> parcoursFresqueBd;
    ParcoursBD pacroursBdComplet;
    Bundle bdBundle;

    ArrayAdapter<String> arrayAdapter;


    public ParcoursListFresqueBDFragment() {
        // Required empty public constructor
    }

    //region Singleton
    private static ParcoursListFresqueBDFragment instance;

    public static ParcoursListFresqueBDFragment getInstance() {
        if (instance == null) {
            instance = new ParcoursListFresqueBDFragment();
        }
        return instance;
    }
    //endregion

    //region Communication
    public interface ParcoursListFresqueBDFragmentCallback {
        void getDetailFresque(String titre);
    }

    private ParcoursListFresqueBDFragmentCallback callback;

    public void setCallback(ParcoursListFresqueBDFragmentCallback callback) {
        this.callback = callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initvariable();
        initParcourt();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_parcours_list_fresque_bd, container, false);
        v = initFragment(v);

        return v;
    }

    private void initvariable(){
        parcoursFresqueBd = new ArrayList<>();
        titre = new ArrayList<>();
        bdBundle = this.getArguments();
    }

    private View initFragment(View v){

        lvFresqueBdList = (ListView) v.findViewById(R.id.lv_parcours_list_fresque_view);
        initListFresque();
        return v;
    }


    private ArrayList<String> listeTitreFresque(){
            ArrayList<String> listFresqueBd = new ArrayList<>();
            for (int i = 0; i< parcoursFresqueBd.size(); i++){
                FresqueBD bd = parcoursFresqueBd.get(i);
                String titre = bd.getTitre();
                listFresqueBd.add(titre);
            }
            return listFresqueBd;
    }

    private void envoyerCallback(String titre) {
        if (callback != null) {
            callback.getDetailFresque(titre);
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
        pacroursBdComplet = bdBundle.getParcelable(ParcoursActivity.PARCOURS_BD_CHOISIS);
        parcoursFresqueBd = pacroursBdComplet.getParcoursFresqueBD();
        titre = listeTitreFresque();

    }

}
