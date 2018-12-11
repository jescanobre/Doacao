package dsdm.ufc.doacao;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dsdm.ufc.doacao.entidades.Solicitacao;
import dsdm.ufc.doacao.entidades.Usuarios;
import dsdm.ufc.doacao.managers.SessionManager;
import dsdm.ufc.doacao.services.SaveSolicitacaoService;

public class FazerSolicitacao extends AppCompatActivity {

    Button okBtn;

    public static final String EXTRA_ID_OBJETO = "id_objeto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer_solicitacao);

        okBtn = (Button)findViewById(R.id.okBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText txtMensagem = (TextInputEditText) findViewById(R.id.txtMensagem);
                SessionManager sessionManager = new SessionManager(FazerSolicitacao.this);
                Usuarios usuarioDados = sessionManager.getUser();
                String mensagem = txtMensagem.getText().toString();
                String usuarioId = usuarioDados.getId();
                String objetoId = getIntent().getStringExtra(EXTRA_ID_OBJETO);

                Intent service = new Intent(FazerSolicitacao.this, SaveSolicitacaoService.class);
                service.putExtra(SaveSolicitacaoService.EXTRA_SOLICITACAO, new Solicitacao(mensagem,usuarioId, objetoId,false));
                startService(service);

                Intent intent = new Intent(FazerSolicitacao.this,MainActivity.class);
                intent.putExtra(ObjetoDetalhe.EXTRA_ID, objetoId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(FazerSolicitacao.this, "Solicitação enviada!", Toast.LENGTH_SHORT).show();
                FazerSolicitacao.this.finish();
            }
        });


    }
}
