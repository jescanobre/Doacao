package dsdm.ufc.doacao.fragments.donate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import dsdm.ufc.doacao.R;
import dsdm.ufc.doacao.entidades.Objeto;

public class StatusDonate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_donate);
    }

    public void btnUsedOnCLick(View view) {
        int id = view.getId();
        Intent intent = new Intent(StatusDonate.this, RevisionDonate.class);
        Objeto objeto = (Objeto) getIntent().getSerializableExtra("objeto");

        if( id == R.id.btn_used ) {
            objeto.setEhUsado(true);
        }
        else {
            objeto.setEhUsado(false);
        }

        intent.putExtra("objeto", objeto);
        startActivity(intent);
    }
}
