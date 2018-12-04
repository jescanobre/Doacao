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
import dsdm.ufc.doacao.entidades.Solicitacao;
import dsdm.ufc.doacao.entidades.Usuarios;

public class minhasSolicitacoes extends AppCompatActivity {

    ListView sLista;
    minhasSolicitacoes mContext;
    public static final String EXTRA_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_solicitacoes);


        sLista = (ListView) findViewById(R.id.sLista);

        Intent intent = getIntent();
        final String id = intent.getExtras().getString(EXTRA_ID);

        final List<String> lista = new ArrayList<String>();                       //declara lista


        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("solicitacao");
        Query query1=mDatabaseRef.orderByChild("idObjeto").equalTo(id);

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){

                    final Solicitacao models=data.getValue(Solicitacao.class);
                    String userId=models.getIdUsuario();
                    final String objId = models.getIdObjeto();
                    System.out.println("id usuario da sugestao : " + userId);

                    //----------------------------------------------------------------------------------

                    DatabaseReference mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("usuario");
                    Query query2=mDatabaseRef2.orderByChild("id").equalTo(userId);

                    query2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot data:dataSnapshot.getChildren()){

                                final Usuarios models=data.getValue(Usuarios.class);
                                String nome=models.getNome();
                                System.out.println("NOME DELE ============ : " + nome);
                                lista.add(nome);
                                final ArrayAdapter arrayAdapter = new ArrayAdapter(minhasSolicitacoes.this,android.R.layout.simple_list_item_1,lista); //add lista no adapter
                                sLista.setAdapter(arrayAdapter);
                                sLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String string=arrayAdapter.getItem(position).toString();
                                        Toast.makeText(minhasSolicitacoes.this, string, Toast.LENGTH_SHORT).show();
                                        System.out.println("objid ============ : " + objId);
                                        Intent intent = new Intent(minhasSolicitacoes.this, solicitacaoDetalhe.class);
                                        Bundle extras = new Bundle();
                                        extras.putString("USER_ID", string);
                                        System.out.println("USER+NOME ANTES DE MANDAR ============ : " + string);
                                        extras.putString("OBJ_ID", objId);
                                        intent.putExtras(extras);
                                        startActivity(intent);
                                        finish();

                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //--------------------------------------------------------------------------------
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


}
