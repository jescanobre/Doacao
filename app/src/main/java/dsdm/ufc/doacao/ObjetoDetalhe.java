package dsdm.ufc.doacao;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import dsdm.ufc.doacao.DAO.ConfiguracaoFirebase;
import dsdm.ufc.doacao.entidades.Objeto;
import dsdm.ufc.doacao.entidades.Solicitacao;
import dsdm.ufc.doacao.entidades.Usuarios;
import dsdm.ufc.doacao.managers.GlideApp;
import dsdm.ufc.doacao.managers.SessionManager;

public class ObjetoDetalhe extends AppCompatActivity {


    Button euQuero;
    TextView usuarioNome;
    GridLayout gridLayout;

    public static final String EXTRA_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto_detalhe);

        gridLayout = findViewById(R.id.grid_layout_images);

        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference(Objeto.REFERENCE_OBJECT);
        DatabaseReference solicitacaoRef = FirebaseDatabase.getInstance().getReference(Solicitacao.REFERENCE_SOLICITACAO);


        Intent intent = getIntent();
        final String id = intent.getExtras().getString(EXTRA_ID);

        Query query1=mDatabaseRef.orderByChild("id").equalTo(id);


        final TextView txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        final TextView txtDescricao = (TextView) findViewById(R.id.txtDescricao);


        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){

                    final Objeto models=data.getValue(Objeto.class);
                    String nome=models.getTitulo();
                    String descricao=models.getDescricao();
                    txtTitulo.setText(nome);
                    txtDescricao.setText(descricao);
                    procuraId(models.getIdDoador());

                    loadImages(models.getImagens());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.w("SOLICITACAO", id);
        solicitacaoRef.orderByChild("idObjeto").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SessionManager session = new SessionManager(getApplicationContext());
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Solicitacao solicitacao = data.getValue(Solicitacao.class);
                    Log.w("solicitacao", solicitacao.toString());
                    if(solicitacao.getIdUsuario().equals(session.getUser().getId())) {
                        euQuero.setClickable(false);
                        euQuero.setEnabled(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        euQuero = (Button)findViewById(R.id.euQuero);

        euQuero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ObjetoDetalhe.this,FazerSolicitacao.class);
                intent.putExtra(FazerSolicitacao.EXTRA_ID_OBJETO, id);
                startActivity(intent);
            }
        });



    }

    public void loadImages(List<String> images) {
        StorageReference storageReference = ConfiguracaoFirebase.getStorageReference();
        for (final String image : images ) {
            storageReference.child(image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    ImageView imageView = new ImageView(getApplicationContext());
                    GlideApp.with(getApplicationContext()).load(uri.toString()).override(300,300).into(imageView);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(10,10,10,10);
                    imageView.setLayoutParams(params);

                    gridLayout.addView(imageView);
                }
            });
        }
    }

    public void procuraId(final String userID){
        final DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("usuario");
        Query query2=refUser.orderByChild("id").equalTo(userID);
        final TextView usuarioNome = (TextView) findViewById(R.id.usuarioNome);
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){


                    final Usuarios models=data.getValue(Usuarios.class);
                    String nome=models.getNome();
                    usuarioNome.setText(nome);



                    usuarioNome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(ObjetoDetalhe.this,PerfilGeral.class);
                            i.putExtra("id",models.getId());
                            startActivity(i);
                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}

