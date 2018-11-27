package dsdm.ufc.doacao;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import dsdm.ufc.doacao.entidades.Objeto;
import dsdm.ufc.doacao.entidades.Usuarios;

public class ObjetoDetalhe extends AppCompatActivity {


    Button euQuero;
    TextView usuarioNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto_detalhe);

        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("objeto");


        Intent intent = getIntent();
        String nome1 = intent.getExtras().getString("nome");

        System.out.println("aaaaaaaaaaaaaaaaaaa   4" + nome1);
        Query query1=mDatabaseRef.orderByChild("titulo").equalTo(nome1);


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



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        euQuero = (Button)findViewById(R.id.euQuero);

        euQuero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ObjetoDetalhe.this,fazerSolicitacao.class);
                startActivity(i);
                finish();

            }
        });



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

