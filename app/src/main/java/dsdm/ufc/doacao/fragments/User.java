package dsdm.ufc.doacao.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import dsdm.ufc.doacao.R;
import dsdm.ufc.doacao.meuObj;

public class User extends Fragment {

    public User() {
    }

    public static User newInstance() {
        User fragment = new User();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        ListView objLista = (ListView) view.findViewById(R.id.objetosLista);

        final String lista[] = new String[]{"Cadeira de Balanço","Bicicleta"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,lista);
        objLista.setAdapter(arrayAdapter);

        objLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(lista[position].equals("Cadeira de Balanço")){
                    Intent i = new Intent(getActivity(),meuObj.class);

                    startActivity(i);
                }
            }
        });
        return view;
    }

}
