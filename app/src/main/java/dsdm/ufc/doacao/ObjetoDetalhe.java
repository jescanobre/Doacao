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

public class ObjetoDetalhe extends AppCompatActivity {


    Button euQuero;
    TextView usuarioNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto_detalhe);

        euQuero = (Button)findViewById(R.id.euQuero);

        euQuero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ObjetoDetalhe.this,fazerSolicitacao.class);
                startActivity(i);
                finish();

            }
        });

        usuarioNome = (TextView)findViewById(R.id.usuarioNome);
        usuarioNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ObjetoDetalhe.this,PerfilGeral.class);
                startActivity(i);
                finish();
            }
        });

    }
}
