package dsdm.ufc.doacao;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dsdm.ufc.doacao.fragments.Donate;
import dsdm.ufc.doacao.fragments.Home;
import dsdm.ufc.doacao.fragments.Search;
import dsdm.ufc.doacao.fragments.User;
import dsdm.ufc.doacao.managers.SessionManager;

public class Opcoes extends Fragment {

    TextView perfil;
    TextView meusObj;
    TextView recebidos;
    TextView sair;

    public static Opcoes newInstance() {
        Opcoes fragment = new Opcoes();
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_opcoes, container, false);

        perfil = (TextView) view.findViewById(R.id.meuPerfil);
        meusObj = (TextView) view.findViewById(R.id.meusObjetos);
        recebidos = (TextView) view.findViewById(R.id.objRecebidos);
        sair = (TextView) view.findViewById(R.id.sair);

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), meuPerfil.class);
                startActivity(i);
            }
        });

        meusObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), meusObj.class);
                startActivity(i);
            }
        });

        recebidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), meusRecebidos.class);
                startActivity(i);
            }
        });

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(getActivity());
                sessionManager.logout();

            }
        });

        return view;

    }
}
