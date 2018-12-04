package dsdm.ufc.doacao;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import dsdm.ufc.doacao.entidades.Objeto;
import dsdm.ufc.doacao.entidades.Solicitacao;
import dsdm.ufc.doacao.entidades.Usuarios;

public class solicitacaoDetalhe extends AppCompatActivity {

    TextView user, txtMensagem;
    Button aceita,recusa;
    String userId;
    minhasSolicitacoes mContext;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        final String objId = extras.getString("OBJ_ID");
        final String userNome = extras.getString("USER_ID");
        System.out.println("NOME DO USUARIO É : " + userNome);
        System.out.println("ID DO OBJ É : " + objId);




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitacao_detalhe);

        txtMensagem = (TextView)findViewById(R.id.txtMensagem);
        aceita = (Button)findViewById(R.id.aceitarBtn);
        recusa = (Button)findViewById(R.id.recusarBtn);

        DatabaseReference mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("usuario");
        Query query2=mDatabaseRef2.orderByChild("nome").equalTo(userNome);

        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){

                    final Usuarios models=data.getValue(Usuarios.class);
                    userId = models.getId();
                    TextView usuarioNome = (TextView)findViewById(R.id.userNome) ;
                    usuarioNome.setText(userNome);
                    carregaMsg(models.getId(), objId);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        recusa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(solicitacaoDetalhe.this, "Solicitação Recusada!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(solicitacaoDetalhe.this,meusObj.class);
                i.putExtra(ObjetoDetalhe.EXTRA_ID, objId);
                startActivity(i);
                finish();

            }
        });

    }


    public void atualizaStatus(final String objId, final String receptorId){
        final DatabaseReference refObj = FirebaseDatabase.getInstance().getReference("objeto");
        Query query2=refObj.orderByChild("id").equalTo(objId);

        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){

                    final Objeto models=data.getValue(Objeto.class);
                    String path = "objeto/"+models.getId()+"/estado";
                    String path2 = "objeto/"+models.getId()+"/idReceptor";

                    System.out.println("DENTRO DO UPDATE ================ " );

                    final DatabaseReference refObj2 = FirebaseDatabase.getInstance().getReference(path);
                    refObj2.setValue(true);
                    final DatabaseReference refObj3 = FirebaseDatabase.getInstance().getReference(path2);
                    refObj3.setValue(receptorId);




                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        }

    public void carregaMsg(final String usuarioId, final String objId){
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("solicitacao");
        Query query1=mDatabaseRef.orderByChild("idObjeto").equalTo(objId);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){

                    final Solicitacao models=data.getValue(Solicitacao.class);
                    System.out.println("A MENSAGEM ================ antes" + models.getIdUsuario());
                    System.out.println("A MENSAGEM compara ================ antes" + usuarioId);

                    if(models.getIdUsuario().equals(usuarioId)){
                        String mensagem=models.getMensagem();
                        System.out.println("A MENSAGEM DE DENTRO ================ " + mensagem);
                        txtMensagem.setText(mensagem);


                        aceita.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                atualizaStatus(models.getIdObjeto(),models.getIdUsuario());
                                Intent i = new Intent(solicitacaoDetalhe.this,meusObj.class);
                                i.putExtra(ObjetoDetalhe.EXTRA_ID, objId);
                                startActivity(i);
                                finish();
                            }
                        });
                        break;

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    }




