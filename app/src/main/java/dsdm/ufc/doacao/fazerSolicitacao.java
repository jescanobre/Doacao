package dsdm.ufc.doacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dsdm.ufc.doacao.entidades.Solicitacao;
import dsdm.ufc.doacao.entidades.Usuarios;
import dsdm.ufc.doacao.managers.SessionManager;

public class fazerSolicitacao extends AppCompatActivity {

    Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer_solicitacao);

        okBtn = (Button)findViewById(R.id.okBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtMensagem = (EditText) findViewById(R.id.txtMensagem);
                SessionManager sessionManager = new SessionManager(fazerSolicitacao.this);
                Usuarios usuarioDados = sessionManager.getUser();
                String mensagem = txtMensagem.getText().toString();
                String usuarioId = usuarioDados.getId();
                Solicitacao solicitacao = new Solicitacao(mensagem,usuarioId,false);

                Intent i = new Intent(fazerSolicitacao.this,ObjetoDetalhe.class);
                startActivity(i);
                finish();
                Toast.makeText(fazerSolicitacao.this, "Solicitação enviada!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
