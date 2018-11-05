package dsdm.ufc.doacao.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dsdm.ufc.doacao.ObjetoDetalhe;
import dsdm.ufc.doacao.R;


public class Home extends Fragment {

    public Home() {

    }

    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        CardView cardView1 = view.findViewById(R.id.card_view);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirObjDetalhe();
            }
        });

        CardView cardView2 = view.findViewById(R.id.card_view1);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirObjDetalhe();
            }
        });

        CardView cardView3 = view.findViewById(R.id.card_view2);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirObjDetalhe();
            }
        });

        CardView cardView4 = view.findViewById(R.id.card_view3);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirObjDetalhe();
            }
        });

        CardView cardView5 = view.findViewById(R.id.card_view4);
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirObjDetalhe();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public void abrirObjDetalhe (){
        Intent intent = new Intent(getActivity(), ObjetoDetalhe.class);
        startActivity(intent);
    }
}
