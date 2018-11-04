package dsdm.ufc.doacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
                Intent i = new Intent(fazerSolicitacao.this,ObjetoDetalhe.class);
                startActivity(i);
                finish();
                Toast.makeText(fazerSolicitacao.this, "Solicitação enviada!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
