package dsdm.ufc.doacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class solicitacaoDetalhe extends AppCompatActivity {

    TextView user;
    Button aceita,recusa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitacao_detalhe);

        user = (TextView)findViewById(R.id.userNome);
        aceita = (Button)findViewById(R.id.aceitarBtn);
        recusa = (Button)findViewById(R.id.recusarBtn);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(solicitacaoDetalhe.this,PerfilGeral.class);
                startActivity(i);

            }
        });

        aceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(solicitacaoDetalhe.this, "Solicitação Aceita!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(solicitacaoDetalhe.this,MeuObjetoDetalhe.class);
                startActivity(i);

            }
        });

        recusa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(solicitacaoDetalhe.this, "Solicitação Recusada!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(solicitacaoDetalhe.this,MeuObjetoDetalhe.class);
                startActivity(i);

            }
        });

    }
}
