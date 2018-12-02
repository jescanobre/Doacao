package dsdm.ufc.doacao.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Map;

import dsdm.ufc.doacao.DAO.ConfiguracaoFirebase;
import dsdm.ufc.doacao.ObjetoDetalhe;
import dsdm.ufc.doacao.R;
import dsdm.ufc.doacao.entidades.Objeto;
import dsdm.ufc.doacao.managers.GlideApp;
import dsdm.ufc.doacao.managers.SessionManager;
import dsdm.ufc.doacao.services.DownloadImageService;


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
        return view;
    }

    public void abrirObjDetalhe (String id){
        Intent intent = new Intent(getActivity(), ObjetoDetalhe.class);
        intent.putExtra(ObjetoDetalhe.EXTRA_ID, id);
        startActivity(intent);
    }

    public void loadObjetos() {

        DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child(Objeto.REFERENCE_OBJECT);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren() ) {
                    Objeto objeto = data.getValue(Objeto.class);
                    createCardView( objeto );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void createCardView(final Objeto objeto) {

        if( objeto.getImagens().size() > 0 ) {

            StorageReference storageReference = ConfiguracaoFirebase.getStorageReference();
            storageReference.child(objeto.getImagens().get(0)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View card = inflater.inflate(R.layout.my_card, null);

                    TextView text = (TextView) card.findViewById(R.id.text);
                    text.setText(objeto.getTitulo());
                    ImageView imageView = (ImageView) card.findViewById(R.id.image);

                    GlideApp.with(view).load(uri.toString()).override(300, 300).into(imageView);

                    GridLayout gridLayout = view.findViewById(R.id.home_grid_layout);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 10, 10, 10);

                    card.setLayoutParams(params);

                    card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            abrirObjDetalhe(objeto.getId());
                        }
                    });

                    gridLayout.addView(card);
                }
            });
        }
    }

}
