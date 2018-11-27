package dsdm.ufc.doacao.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import dsdm.ufc.doacao.DAO.ConfiguracaoFirebase;
import dsdm.ufc.doacao.ObjetoDetalhe;
import dsdm.ufc.doacao.R;
import dsdm.ufc.doacao.entidades.Objeto;
import dsdm.ufc.doacao.managers.GlideApp;
import dsdm.ufc.doacao.managers.SessionManager;


public class Home extends Fragment {

    private SessionManager session;
    private View view;

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

        view =  inflater.inflate(R.layout.fragment_home, container, false);

        session = new SessionManager(view.getContext());

        loadObjetos();

//
//        CardView cardView1 = view.findViewById(R.id.card_view);
//        cardView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                abrirObjDetalhe();
//            }
//        });
//
//        CardView cardView2 = view.findViewById(R.id.card_view1);
//        cardView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                abrirObjDetalhe();
//            }
//        });
//
//        CardView cardView3 = view.findViewById(R.id.card_view2);
//        cardView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                abrirObjDetalhe();
//            }
//        });
//
//        CardView cardView4 = view.findViewById(R.id.card_view3);
//        cardView4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                abrirObjDetalhe();
//            }
//        });
//
//        CardView cardView5 = view.findViewById(R.id.card_view4);
//        cardView5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                abrirObjDetalhe();
//            }
//        });


        // Inflate the layout for this fragment
        return view;
    }

    public void abrirObjDetalhe (){
        Intent intent = new Intent(getActivity(), ObjetoDetalhe.class);
        startActivity(intent);
    }

    public View myCard(String title, String path){
        View card;
        LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        card = inflater.inflate(R.layout.my_card, null);

        TextView text = (TextView) card.findViewById(R.id.text);
        text.setText(title);
        final ImageView imageView = (ImageView) card.findViewById(R.id.image);

        StorageReference storageReference = ConfiguracaoFirebase.getStorageReference();
        storageReference.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.w("Uri", uri.toString());

                GlideApp.with(view).load(uri.toString()).override(300,300).into(imageView);
            }
        });

        return card;
    }

    public void loadObjetos() {
        List<Objeto> objetos = Objeto.loadObjects();

        GridLayout gridLayout = view.findViewById(R.id.home_grid_layout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);

        if( objetos.size() > 0 ) {

            for( Objeto objeto : objetos ) {
                for( String path : objeto.getImagens() ) {
                    View card = myCard(objeto.getTitulo(), path);

                    card.setLayoutParams(params);

                    gridLayout.addView(card);

                }
            }

        }
    }
}
