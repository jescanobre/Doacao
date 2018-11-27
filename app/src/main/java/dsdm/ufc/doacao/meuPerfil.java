package dsdm.ufc.doacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dsdm.ufc.doacao.entidades.Usuarios;
import dsdm.ufc.doacao.managers.SessionManager;

public class meuPerfil extends AppCompatActivity {

    ListView objLista;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_perfil);

        sessionManager = new SessionManager(getApplicationContext());

        TextView txtNome = (TextView) findViewById(R.id.txtNome);
        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);



        final String lista[] = new String[]{"Cadeira de Balanço","Bicicleta"};

        sessionManager = new SessionManager(this);
        Usuarios usuarioDados = sessionManager.getUser();
        System.out.println("aaaaaaaaaaaaaaaa " + usuarioDados.getNome());
        txtNome.setText(usuarioDados.getNome());
        txtEmail.setText(usuarioDados.getEmail());


    }


}
