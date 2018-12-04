package dsdm.ufc.doacao.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import dsdm.ufc.doacao.MeuObjetoDetalhe;
import dsdm.ufc.doacao.ObjetoDetalhe;
import dsdm.ufc.doacao.R;
import dsdm.ufc.doacao.entidades.Objeto;
import dsdm.ufc.doacao.entidades.Usuarios;
import dsdm.ufc.doacao.managers.SessionManager;
import dsdm.ufc.doacao.meuPerfil;

public class Search extends Fragment {

    public Search() {
    }

    public static Search newInstance() {
        Search fragment = new Search();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_search, container, false);

            SearchView srcView = (SearchView) view.findViewById(R.id.srcView);
            final TextView txtResult = (TextView) view.findViewById(R.id.txtResult);

            if(srcView!=null) {
                final CharSequence query = srcView.getQuery();

                System.out.println("aaaaaaaaaaaaaaaaaaa   " + query);

                srcView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        System.out.println("aaaaaaaaaaaaaaaaaaa   " + query);


                        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("objeto");

                        Query query1 = mDatabaseRef.orderByChild("titulo").equalTo(query.toString());

                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot data : dataSnapshot.getChildren()) {


                                    final Objeto models = data.getValue(Objeto.class);
                                    String nome = models.getTitulo();
                                    System.out.println("aaaaaaaaaaaaaaaaaaa   2" + nome);
                                    txtResult.setText(nome);
                                    final String id = models.getId();
                                    txtResult.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            SessionManager sessionManager;
                                            sessionManager = new SessionManager(getContext());
                                            sessionManager = new SessionManager(getContext());
                                            final Usuarios usuarioDados = sessionManager.getUser();
                                            Boolean meuOuNao = false;
                                            if(models.getIdDoador().equals(usuarioDados.getId())){
                                                meuOuNao = true;
                                            }
                                            abrirObjDetalhe(id, meuOuNao);
                                        }
                                    });
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                txtResult.setText("Nenhum Objeto Encontrado!");
                            }
                        });
                    }
                });
            }

        return view;

        }
    public void abrirObjDetalhe (String id, boolean meuOuNao){
        if(meuOuNao==false){
            Intent intent = new Intent(getActivity(), ObjetoDetalhe.class);
            intent.putExtra(ObjetoDetalhe.EXTRA_ID, id);
            startActivity(intent);
        }
        if(meuOuNao==true){
            Intent intent = new Intent(getActivity(), MeuObjetoDetalhe.class);
            intent.putExtra(ObjetoDetalhe.EXTRA_ID, id);
            startActivity(intent);
        }

    }


    }

