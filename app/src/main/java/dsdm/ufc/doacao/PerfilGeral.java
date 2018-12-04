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
import java.util.List;

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

                    procuraObjs(models.getId());
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

    public void procuraObjs(String id) {
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("objeto");
        Query query1 = mDatabaseRef.orderByChild("idDoador").equalTo(id);
        final ListView listaObjs = (ListView) findViewById(R.id.listaObjs);

        final List<String> lista = new ArrayList<String>();                       //declara lista

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    final Objeto models = data.getValue(Objeto.class);
                    String nome = models.getTitulo();
                    System.out.println("objeto detalhe nome : " + nome);
                    lista.add(models.getTitulo());                            //add nome do obj na lista
                    System.out.println("Lista ainda dentro da função : " + lista);
                    final ArrayAdapter arrayAdapter = new ArrayAdapter(PerfilGeral.this, android.R.layout.simple_list_item_1, lista); //add lista no adapter
                    listaObjs.setAdapter(arrayAdapter);
                    listaObjs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String string = arrayAdapter.getItem(position).toString();
                            Toast.makeText(PerfilGeral.this, string, Toast.LENGTH_SHORT).show();
                            acha(string);

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void acha(String nome){
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("objeto");

        Query query1=mDatabaseRef.orderByChild("titulo").equalTo(nome);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){

                    final Objeto models=data.getValue(Objeto.class);
                    String nome=models.getTitulo();
                    System.out.println("objeto detalhe nome : " + nome);
                    Intent intent = new Intent(PerfilGeral.this, MeuObjetoDetalhe.class);
                    intent.putExtra(ObjetoDetalhe.EXTRA_ID, models.getId());
                    startActivity(intent);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
