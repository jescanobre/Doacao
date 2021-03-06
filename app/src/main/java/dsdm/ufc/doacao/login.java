package dsdm.ufc.doacao;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import dsdm.ufc.doacao.DAO.ConfiguracaoFirebase;
import dsdm.ufc.doacao.entidades.Usuarios;
import dsdm.ufc.doacao.managers.SessionManager;

public class login extends AppCompatActivity {

    Button loginBtn;
    Button cadastroBtn;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());

        if( session.isLoggedIn() )
            abrirtela();

        setContentView(R.layout.activity_login);
        loginBtn = (Button) findViewById(R.id.loginButton);
        cadastroBtn = (Button) findViewById(R.id.cadastroButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText tlogin = (TextInputEditText) findViewById(R.id.emailText);
                TextInputEditText tsenha = (TextInputEditText) findViewById(R.id.passText);

                String login = tlogin.getText().toString();
                String senha = tsenha.getText().toString();

                if (!login.equals("") && !senha.equals("")) {

                    usuarios = new Usuarios();
                    usuarios.setEmail(login);
                    usuarios.setSenha(senha);

                    validarLogin();

                    getUsuarioByEmail();
                    Log.w("TESTE", session.getUser().toString() );

                }else{
                    Toast.makeText(login.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cadastroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dsdm.ufc.doacao.login.this, cadastro.class);
                startActivity(intent);
            }
        });

    }

    private void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(),usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirtela();
                    Toast.makeText(login.this, "Logou!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void abrirtela(){

        Intent i = new Intent(login.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void getUsuarioByEmail() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase();

        Log.w("EMAIL", usuarios.getEmail() );
        Query query = databaseReference.child("usuario").orderByChild("email").equalTo(usuarios.getEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() ) {
                    Usuarios usuario = new Usuarios();
                    for ( DataSnapshot data : dataSnapshot.getChildren() ) {
                        usuario = data.getValue( Usuarios.class );
                    }
                    session.createLoginSession(usuario);
                } else {
                    Log.w("ERRO", "deu ruim!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
