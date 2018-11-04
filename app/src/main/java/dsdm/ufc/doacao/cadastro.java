package dsdm.ufc.doacao;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import dsdm.ufc.doacao.DAO.ConfiguracaoFirebase;
import dsdm.ufc.doacao.Helper.Base64Custom;
import dsdm.ufc.doacao.Helper.Preferencias;
import dsdm.ufc.doacao.entidades.Usuarios;

public class cadastro extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha1;
    private EditText senha2;
    private Button cadastra;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = (EditText) findViewById(R.id.txtNome);
        email = (EditText) findViewById(R.id.txtEmail);
        senha1 = (EditText) findViewById(R.id.txtSenha);
        senha2 = (EditText) findViewById(R.id.txtSenha2);
        cadastra = (Button) findViewById(R.id.btnCadastrar);

        cadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (senha1.getText().toString().equals(senha2.getText().toString())) {

                    usuarios = new Usuarios();
                    usuarios.setNome(nome.getText().toString());
                    usuarios.setEmail(email.getText().toString());
                    usuarios.setSenha(senha1.getText().toString());

                    cadastrarUsuario();

                } else {
                    Toast.makeText(cadastro.this, "Senhas são diferentes!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuarios.getEmail(),
                usuarios.getSenha()
        ).addOnCompleteListener(cadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(cadastro.this, "Usuario Cadastrado!", Toast.LENGTH_SHORT).show();
                    String identificadorUsuario = Base64Custom.codificarBase64(usuarios.getEmail());

                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuarios.setId(identificadorUsuario);
                    usuarios.salvar();

                    Preferencias preferenciasAndroid = new Preferencias(cadastro.this);
                    preferenciasAndroid.salvarUsuarioPreferencias(identificadorUsuario,usuarios.getNome());

                    abrirLoginUsuario();
                }else{
                    String erroExcecao = "";

                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite uma senha de no mínimo 6 caracteres!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "E-mail inválido!";
                    }catch (FirebaseAuthUserCollisionException e){
                        erroExcecao = "E-mail já cadastrado!";
                    }catch (Exception e){
                        erroExcecao = "Erro ao efetuar o cadastro!";
                        e.printStackTrace();
                    }
                    Toast.makeText(cadastro.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void abrirLoginUsuario(){
        Intent intent = new Intent(cadastro.this,login.class);
        startActivity(intent);
        finish();
    }
}
