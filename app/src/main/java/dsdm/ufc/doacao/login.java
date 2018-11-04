package dsdm.ufc.doacao;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dsdm.ufc.doacao.DAO.ConfiguracaoFirebase;
import dsdm.ufc.doacao.entidades.Usuarios;

public class login extends AppCompatActivity {

    Button loginBtn;
    Button cadastroBtn;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = (Button) findViewById(R.id.loginButton);
        cadastroBtn = (Button) findViewById(R.id.cadastroButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText tlogin = (EditText) findViewById(R.id.emailText);
                EditText tsenha = (EditText) findViewById(R.id.passText);

                String login = tlogin.getText().toString();
                String senha = tsenha.getText().toString();

                if (!login.equals("") && !senha.equals("")) {

                    usuarios = new Usuarios();
                    usuarios.setEmail(login);
                    usuarios.setSenha(senha);

                    validarLogin();

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
        Intent i = new Intent(login.this,ObjetoDetalhe.class);
        startActivity(i);
        finish();
    }


}


