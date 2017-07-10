package com.formation.appli.bruxellesparcourbd.ui.parcourt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.formation.appli.bruxellesparcourbd.Asynch.JsonParcourtBD;
import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.model.FresqueBD;
import com.formation.appli.bruxellesparcourbd.model.ParcourtBD;
import com.formation.appli.bruxellesparcourbd.ui.User.UserActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParcourtListFresqueBDFragment extends Fragment implements JsonParcourtBD.JsonParcourtBDCallBack{

    private ParcourtBD parcourtBD;
    private FresqueBD fresqueBD;
    private int numéroParcourChoisi;
    private ListView lvFresqueBdList;
    ArrayList<String> titre;
    ArrayList<FresqueBD> parcourFresqueBd;
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
            callback.onListClick(titre,numéroParcourChoisi);
        }
    }

    private void initParcourt(){
        titre = new ArrayList<>();
        parcourFresqueBd = new ArrayList<>();
        bdBundle = this.getArguments();
        numéroParcourChoisi = bdBundle.getInt(UserActivity.NUMERODEPARCOURT);
        JsonParcourtBD parcourJson= new JsonParcourtBD();
        parcourJson.setCallback(this);
        parcourJson.execute(numéroParcourChoisi);
        parcourFresqueBd = parcourJson.getArrayFresque();
        titre = parcourJson.ListParcourtFresqueBd();

    }

    @Override
    public void parcourt() {
        Toast.makeText(this.getActivity(),"le parcourt a été correctemenet chargé", Toast.LENGTH_SHORT).show();
    }

}
