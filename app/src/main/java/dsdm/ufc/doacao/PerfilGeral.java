package dsdm.ufc.doacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dsdm.ufc.doacao.entidades.Objeto;
import dsdm.ufc.doacao.entidades.Usuarios;

public class PerfilGeral extends AppCompatActivity {


    ListView objLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_geral);

        objLista = (ListView) findViewById(R.id.objetosLista);

        Intent intent = getIntent();
        String userID = intent.getExtras().getString("id");

        final TextView usuarioNome = (TextView) findViewById(R.id.usuarioNome);
        final TextView usuarioEmail = (TextView) findViewById(R.id.usuarioEmail);
        final TextView objsDe = (TextView) findViewById(R.id.objsDe);
        ArrayList<String> lista = new ArrayList<>();

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);


        final DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("usuario");
        Query query2=refUser.orderByChild("id").equalTo(userID);
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){


                    final Usuarios models=data.getValue(Usuarios.class);
                    String nome=models.getNome();
                    usuarioNome.setText(nome);
                    usuarioEmail.setText(models.getEmail());
                    objsDe.setText("Objetos de: " + nome);

                    procuraObjs(models.getId(), arrayAdapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        //final String lista[] = new String[]{"Cadeira de Balanço","Bicicleta"};



        //objLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //    @Override
        //    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //        if(lista[position].equals("Cadeira de Balanço")){
        //            Intent i = new Intent(PerfilGeral.this,ObjetoDetalhe.class);

        //            startActivity(i);
        //        }
        //    }
       // });
    }

    public void procuraObjs(String id, final ArrayAdapter arrayAdapter){
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("objeto");
        Query query1=mDatabaseRef.orderByChild("idDoador").equalTo(id);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){


                    final Objeto models=data.getValue(Objeto.class);
                    String nome=models.getTitulo();
                    String descricao=models.getDescricao();
                    ArrayList<String> lista = new ArrayList<>();
                    lista.add(nome);

                    objLista = (ListView) findViewById(R.id.objetosLista);


                    objLista.setAdapter(arrayAdapter);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
