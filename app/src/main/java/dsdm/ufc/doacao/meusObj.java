package dsdm.ufc.doacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import dsdm.ufc.doacao.managers.SessionManager;

public class meusObj extends AppCompatActivity {

    ListView listaObjs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_obj);





        listaObjs = (ListView)findViewById(R.id.listaObjs);


        SessionManager sessionManager = new SessionManager(this);
        Usuarios usuarioDados = sessionManager.getUser();
        final List<String> lista = new ArrayList<String>();                       //declara lista


        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("objeto");
        Query query1=mDatabaseRef.orderByChild("idDoador").equalTo(usuarioDados.getId());
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){

                    final Objeto models=data.getValue(Objeto.class);
                    String nome=models.getTitulo();
                    System.out.println("objeto detalhe nome : " + nome);
                    lista.add(models.getTitulo());                            //add nome do obj na lista
                    System.out.println("Lista ainda dentro da função : " + lista);
                    final ArrayAdapter arrayAdapter = new ArrayAdapter(meusObj.this,android.R.layout.simple_list_item_1,lista); //add lista no adapter
                    listaObjs.setAdapter(arrayAdapter);
                    listaObjs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String string=arrayAdapter.getItem(position).toString();
                            Toast.makeText(meusObj.this, string, Toast.LENGTH_SHORT).show();
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
                    Intent intent = new Intent(meusObj.this, MeuObjetoDetalhe.class);
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
